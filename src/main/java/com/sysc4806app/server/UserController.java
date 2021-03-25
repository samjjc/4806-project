package com.sysc4806app.server;

import com.sysc4806app.model.Product;
import com.sysc4806app.model.User;
import com.sysc4806app.repos.ProductRepo;
import com.sysc4806app.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @PostMapping("/follow/{follower}/{followee}")
    public RedirectView followUser(@PathVariable("follower") long followerID,
                                             @PathVariable("followee") long followeeID) {
        User follower = userRepo.findById(followerID);
        follower.followUser(userRepo.findById(followeeID));
        userRepo.save(follower);
        return new RedirectView(String.format("/user/%s", followeeID));
    }

    @GetMapping("/user/{id}")
    public String userView(Model model, @PathVariable("id") long id) {
        System.out.println("LOGINGGGGGGG");
        System.out.println(userRepo.findAll());
        model.addAttribute("user", userRepo.findById(id));

        return "user";
    }
}
