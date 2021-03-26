package com.sysc4806app.server;

import com.sysc4806app.model.Review;
import com.sysc4806app.model.User;
import com.sysc4806app.repos.ProductRepo;
import com.sysc4806app.repos.ReviewRepo;
import com.sysc4806app.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

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
    public String submitNewReviewForm(
            @PathVariable("id") long id, @Valid @ModelAttribute Review review,Principal principal, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "addNewReviewForm";
        }
        if(principal!=null) {
            User loginUser = userRepo.findByName(principal.getName());
            review.setUser(loginUser);
        }
        review.setProduct(productRepo.findById(id));
        review.setId(null);
        reviewRepo.save(review);
        return String.format("redirect:/product/%s", id);
    }

    @GetMapping(value="/product/{id}/review")
    public String getNewReviewForm(@PathVariable String id, Model model) {
        model.addAttribute("review", new Review());
        return "addNewReviewForm";
    }
}
