package com.sysc4806app.server;

import com.sysc4806app.model.*;
import com.sysc4806app.repos.ProductRepo;
import com.sysc4806app.repos.ReviewRepo;
import com.sysc4806app.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ReviewRepo reviewRepo;

    @Autowired
    private ProductService productService;

    @GetMapping("/productlist")
    public String productList(Model model) {

        model.addAttribute("products", productRepo.findAll());

        return "productlist";
    }

    @GetMapping(path="/product/{id}")
    public String requestProduct(@PathVariable("id") long id, Model model) {
        Product product = productRepo.findById(id);
        System.out.println(product);
        double rating = productService.calculateRating(id);
        System.out.println(rating);
        model.addAttribute("rating", String.format("%.1f", rating));
        model.addAttribute("reviews", reviewRepo.findByProduct(product));
        model.addAttribute("product", product);
        return "product/product";
    }

    @PostMapping("/product")
    public RedirectView submitNewProductForm(@ModelAttribute Product product) {
        productRepo.save(product);
        System.out.println(product);
        return new RedirectView(String.format("/product/%s", product.getId()));
    }

    @GetMapping(value="/product")
    public String getNewProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("types", ProductType.values());
        model.addAttribute("chains", ProductChain.values());
        return "addNewProductForm";
    }
}
