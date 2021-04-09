package com.sysc4806app.server;

import com.sysc4806app.model.Product;
import com.sysc4806app.model.Review;
import com.sysc4806app.model.User;
import com.sysc4806app.repos.ProductRepo;
import com.sysc4806app.repos.ReviewRepo;
import com.sysc4806app.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

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
            @PathVariable("id") long id, @Valid @ModelAttribute Review review, BindingResult bindingResult,
            Principal principal, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("restOperation", "post");
            return "addNewReviewForm";
        }
        if(principal==null) {
            return String.format("redirect:/product/%s/review?nologin", id);
        }
        User loginUser = userRepo.findByName(principal.getName());
        Product product = productRepo.findById(id);
        if (loginUser != null && product != null && loginUser.hasReviewedProduct(product)) {
            // should never occur unless they construct posts
            return "redirect:/error";
        }
        review.setUser(loginUser);
        review.setProduct(productRepo.findById(id));
        review.setId(null);
        reviewRepo.save(review);
        return String.format("redirect:/product/%s", id);
    }

    @PutMapping("/product/{id}/review")
    public String updateReviewForm(
            @PathVariable("id") long id, @Valid @ModelAttribute Review review, BindingResult bindingResult,
            Principal principal, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("restOperation", "put");
            return "addNewReviewForm";
        }
        if(principal==null) {
            return String.format("redirect:/product/%s/review?nologin", id);
        }
        User loginUser = userRepo.findByName(principal.getName());
        Product product = productRepo.findById(id);
        Review existingReview = reviewRepo.findByProductAndUserIn(product, List.of(loginUser)).get(0);
        //update rating and text
        existingReview.setRating(review.getRating());
        existingReview.setText(review.getText());
        reviewRepo.save(existingReview);
        return String.format("redirect:/product/%s", id);
    }

    @GetMapping(value="/product/{id}/review")
    public String getNewReviewForm(@PathVariable String id, Model model, Principal principal) {
        Product product = productRepo.findById(Integer.parseInt(id));
        User user = principal != null ? userRepo.findByName(principal.getName()) : null;
        String restOperation;
        if (user != null && product != null && user.hasReviewedProduct(product)) {
            restOperation = "put";
        } else {
            restOperation = "post";
        }
        model.addAttribute("restOperation", restOperation);
        model.addAttribute("review", new Review());
        return "addNewReviewForm";
    }
}
