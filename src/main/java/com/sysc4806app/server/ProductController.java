package com.sysc4806app.server;

import com.sysc4806app.model.*;
import com.sysc4806app.repos.ProductRepo;
import com.sysc4806app.repos.ReviewRepo;
import com.sysc4806app.repos.UserRepo;
import com.sysc4806app.services.ProductService;
import com.sysc4806app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class ProductController {

    private final ProductRepo productRepo;
    private final ReviewRepo reviewRepo;
    private final UserRepo userRepo;
    private final ProductService productService;
    private final UserService userService;

    @Autowired
    public ProductController(ProductRepo productRepo, ReviewRepo reviewRepo, UserRepo userRepo,ProductService productService, UserService userService) {
        this.productRepo = productRepo;
        this.reviewRepo = reviewRepo;
        this.userRepo =userRepo;
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping("/productlist")
    public String productListTest(@RequestParam(name="type", required=false) ProductType type,
                                  @RequestParam(name="chain", required=false) ProductChain chain,
                                  @RequestParam(name="name", required=false) String name,
                                  Pageable pageable, Model model, Principal principal) {
        List<Product> productList;
        // sadly findByTypeAndChain does not handle null values
        // we could switch these to different endpoints if needed
        Sort.Order followOrder = pageable.getSort().getOrderFor("averageFollowRating");
        Sort.Order order = pageable.getSort().getOrderFor("averageRating");

        //if averageFollowRating sort then just do unsorted and sort after
        if(followOrder!=null){ pageable = PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(),Sort.unsorted()); }

        int productListTotal = 0;
        if (name == null) { name = ""; }
        if (type != null && chain != null) {
            productList = productRepo.findByTypeAndChainAndNameContainsIgnoreCase(type, chain, name, pageable);
            productListTotal = productRepo.findByTypeAndChainAndName(type, chain, name).size();
        } else if (type != null) {
            productList = productRepo.findByTypeAndNameContainsIgnoreCase(type, name, pageable);
            productListTotal = productRepo.findByTypeAndName(type, name).size();
        } else if (chain != null) {
            productList = productRepo.findByChainAndNameContainsIgnoreCase(chain, name, pageable);
            productListTotal = productRepo.findByChainAndName(chain, name).size();
        } else {
            productList = productRepo.findByNameContainsIgnoreCase(name, pageable);
            productListTotal =productRepo.findByName(name).size();
        }

        //sort follow order
        if(followOrder!=null && principal!=null){
            User loggedIn = userRepo.findByName(principal.getName());
            productList.sort( (p1 , p2) -> {
                Double avg1=productService.calculateFollowingRating(p1.getId(),loggedIn.getFollowing());
                Double avg2=productService.calculateFollowingRating(p2.getId(),loggedIn.getFollowing());
                //descending order
                return avg2.compareTo(avg1);
            });
        }
        User user = userRepo.findByName(SecurityContextHolder.getContext().getAuthentication().getName());
        boolean isAdmin = user != null && user.isAdmin();
        model.addAttribute("isAdmin", isAdmin);

        model.addAttribute("products", productList);
        model.addAttribute("types", ProductType.values());
        model.addAttribute("chains", ProductChain.values());
        model.addAttribute("type", type);
        model.addAttribute("chain", chain);
        model.addAttribute("name", name);

        //add total page number and list of pages number
        int totalPages = (int) Math.floor(productListTotal/pageable.getPageSize());
        model.addAttribute("totalPages", totalPages);
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(0, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("currentPage", pageable.getPageNumber());

        if(followOrder !=null){
            model.addAttribute("sort", followOrder.getProperty() + "," + followOrder.getDirection());
        }else if (order != null) {
            model.addAttribute("sort", order.getProperty() + "," + order.getDirection());
        } else {
            model.addAttribute("sort", null);
        }

        return "productlist";
    }

    @GetMapping(path="/product/{id}")
    public String requestProduct(@PathVariable("id") long id, @RequestParam(value = "sort", required = false) String sort, Model model, Principal principal) {
        Product product = productRepo.findById(id);
        double rating = productService.calculateRating(id);
        List<Review> reviews;

        if (sort == null) {
            reviews = reviewRepo.findByProduct(product);
        } else if (sort.equals("jaccard") && principal != null) {
            reviews = reviewRepo.findByProduct(product);
            User current = userRepo.findByName(SecurityContextHolder.getContext().getAuthentication().getName());
            reviews.sort((a, b) -> Float.compare(userService.getJaccardDistance(current, a.getUser()),userService.getJaccardDistance(current, b.getUser())));
        } else if (sort.equals("euclidean") && principal != null) {
            reviews = reviewRepo.findByProduct(product);
            User current = userRepo.findByName(SecurityContextHolder.getContext().getAuthentication().getName());
            reviews.sort((a, b) -> Float.compare(userService.getEuclideanDistance(current, a.getUser()),userService.getEuclideanDistance(current, b.getUser())));
        } else {
            Sort.Direction dir = Sort.Direction.DESC;
            if (sort.startsWith("-")) {
                sort = sort.replaceFirst("-","");
                dir = Sort.Direction.ASC;
            }
            reviews = productService.getProductReviews(id, sort, dir);
        }

        //Create list of degress of separation numbers
        List<String> degreesOfSeparation = new ArrayList<>();
        if (principal != null){
            User current = userRepo.findByName(principal.getName());
            for (Review review : reviews){
                if (review.getUser()!=null){
                    degreesOfSeparation.add(Integer.toString(userService.getDegreeOfSeparationNumber(current,review.getUser())));
                }else{
                    degreesOfSeparation.add("");
                }

            }
        }

        model.addAttribute("dos",degreesOfSeparation);
        model.addAttribute("rating", String.format("%.1f", rating));
        model.addAttribute("reviews", reviews);
        model.addAttribute("product", product);
        return "product/product";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/product")
    public String submitNewProductForm(@Valid @ModelAttribute Product product, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("types", ProductType.values());
            model.addAttribute("chains", ProductChain.values());
            return "addNewProductForm";
        }
        productRepo.save(product);
        return String.format("redirect:/product/%s", product.getId());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value="/product")
    public String getNewProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("types", ProductType.values());
        model.addAttribute("chains", ProductChain.values());
        return "addNewProductForm";
    }

    @GetMapping(path="/chains")
    public String requestChains(Model model) {
        model.addAttribute("categories", productService.getChainImageByCategory());
        return "product/chains";
    }
}
