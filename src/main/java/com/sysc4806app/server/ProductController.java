package com.sysc4806app.server;

import com.sysc4806app.model.Product;
import com.sysc4806app.model.ProductChain;
import com.sysc4806app.model.ProductType;
import com.sysc4806app.model.Review;
import com.sysc4806app.repos.ProductRepo;
import com.sysc4806app.repos.ReviewRepo;
import com.sysc4806app.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ProductController {

    private final ProductRepo productRepo;
    private final ReviewRepo reviewRepo;
    private final ProductService productService;

    @Autowired
    public ProductController(ProductRepo productRepo, ReviewRepo reviewRepo, ProductService productService) {
        this.productRepo = productRepo;
        this.reviewRepo = reviewRepo;
        this.productService = productService;
    }

    @GetMapping("/productlist")
    public String productList(Model model) {

        model.addAttribute("products", productRepo.findAll());

        return "productlist";
    }

    @GetMapping(path="/product/{id}")
    public String requestProduct(@PathVariable("id") long id, @RequestParam(value = "sort", required = false) String sort, Model model) {
        Product product = productRepo.findById(id);
        double rating = productService.calculateRating(id);
        List<Review> reviews;

        if (sort == null) {
            reviews = reviewRepo.findByProduct(product);
        } else {
            Sort.Direction dir = Sort.Direction.DESC;
            if (sort.startsWith("-")) {
                sort = sort.replaceFirst("-","");
                dir = Sort.Direction.ASC;
            }
            reviews = productService.getProductReviews(id, sort, dir);
        }

        model.addAttribute("rating", String.format("%.1f", rating));
        model.addAttribute("reviews", reviews);
        model.addAttribute("product", product);
        return "product/product";
    }

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

    @GetMapping(value="/product")
    public String getNewProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("types", ProductType.values());
        model.addAttribute("chains", ProductChain.values());
        return "addNewProductForm";
    }
}
