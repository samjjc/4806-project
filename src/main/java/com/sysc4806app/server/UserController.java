package com.sysc4806app.server;

import com.sysc4806app.model.Product;
import com.sysc4806app.model.User;
import com.sysc4806app.repos.ProductRepo;
import com.sysc4806app.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @PostMapping("/follow/{follower}/{followee}")
    public RedirectView submitNewProductForm(@PathVariable("follower") long followerID,
                                             @PathVariable("followee") long followeeID) {
        User follower = userRepo.findById(followerID);
        follower.followUser(userRepo.findById(followeeID));
        userRepo.save(follower);
        return new RedirectView(String.format("/user/%s", followeeID));
    }
}
