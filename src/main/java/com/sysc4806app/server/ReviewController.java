package com.sysc4806app.server;

import com.sysc4806app.model.Review;
import com.sysc4806app.repos.ProductRepo;
import com.sysc4806app.repos.ReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ReviewController {

    private final ProductRepo productRepo;

    private final ReviewRepo reviewRepo;

    @Autowired
    public ReviewController(ProductRepo productRepo, ReviewRepo reviewRepo) {
        this.productRepo = productRepo;
        this.reviewRepo = reviewRepo;
    }

    @PostMapping("/product/{id}/review")
    public RedirectView submitNewProductForm(
            @PathVariable("id") long id, @ModelAttribute Review review) {
        review.setProduct(productRepo.findById(id));
        review.setId(null);
        reviewRepo.save(review);
        // need to add User to the review once login is done
        // or create Anon user that anonymous users can use
        // currently lack of a User is handled in the html

        return new RedirectView(String.format("/product/%s", id));
    }

    @GetMapping(value="/product/{id}/review")
    public String getNewProductForm(@PathVariable String id, Model model) {
        model.addAttribute("review", new Review());
        return "addNewReviewForm";
    }
}
