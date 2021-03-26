package com.sysc4806app.server;

import com.sysc4806app.model.Review;
import com.sysc4806app.model.User;
import com.sysc4806app.repos.ProductRepo;
import com.sysc4806app.repos.ReviewRepo;
import com.sysc4806app.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.Collection;

@Controller
public class ReviewController {

    private final ProductRepo productRepo;

    private final ReviewRepo reviewRepo;

    private final UserRepo userRepo;

    @Autowired
    public ReviewController(ProductRepo productRepo, ReviewRepo reviewRepo, UserRepo userRepo) {
        this.productRepo = productRepo;
        this.reviewRepo = reviewRepo;
        this.userRepo=userRepo;
    }

    @PostMapping("/product/{id}/review")
    public RedirectView submitNewProductForm(Principal principal,
                                             @PathVariable("id") long id, @ModelAttribute Review review) {
        User loginUser = userRepo.findByName(principal.getName());
        review.setProduct(productRepo.findById(id));
        review.setId(null);
        review.setUser(loginUser);
        reviewRepo.save(review);
        return new RedirectView(String.format("/product/%s", id));
    }

    @GetMapping(value="/product/{id}/review")
    public String getNewProductForm(@PathVariable String id, Model model) {
        model.addAttribute("review", new Review());
        return "addNewReviewForm";
    }
}
