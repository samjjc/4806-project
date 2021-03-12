package com.sysc4806app.server;

import com.sysc4806app.model.Product;
import com.sysc4806app.model.ProductChain;
import com.sysc4806app.model.ProductType;
import com.sysc4806app.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ProductController {
    @Autowired
    private ProductRepo productRepo;

    @GetMapping("/productlist")
    public String productList(Model model) {

        model.addAttribute("products", productRepo.findAll());

        return "productlist";
    }

    @PostMapping("/product")
    public RedirectView submitNewProductForm(@ModelAttribute Product product) {
        productRepo.save(product);
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
