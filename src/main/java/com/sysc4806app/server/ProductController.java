package com.sysc4806app.server;

import com.sysc4806app.model.Product;
import com.sysc4806app.model.ProductChain;
import com.sysc4806app.model.ProductType;
import com.sysc4806app.model.User;
import com.sysc4806app.repos.ProductRepo;
import com.sysc4806app.repos.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/productlist")
    public String productList(Model model,Principal principal) {
        logger.info(principal.getName());
        User signedIn = userRepo.findByName(principal.getName());
        logger.info(signedIn.getId().toString());
        logger.info(signedIn.getName());
        model.addAttribute("products", productRepo.findAll());

        return "productlist";
    }
}
