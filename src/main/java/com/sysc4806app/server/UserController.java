package com.sysc4806app.server;

import com.sysc4806app.model.Product;
import com.sysc4806app.model.User;
import com.sysc4806app.repos.ProductRepo;
import com.sysc4806app.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @PostMapping("/follow/{followee}")
    public RedirectView followUser(@PathVariable("followee") String name) {
        User follower = userRepo.findByName(SecurityContextHolder.getContext().getAuthentication().getName());
        follower.followUser(userRepo.findByName(name));
        userRepo.save(follower);

        return new RedirectView(String.format("/user/%s", name));
    }

    @PostMapping("/unfollow/{followee}")
    public RedirectView unfollowUser(@PathVariable("followee") String name) {
        User follower = userRepo.findByName(SecurityContextHolder.getContext().getAuthentication().getName());
        follower.unfollowUser(userRepo.findByName(name));
        userRepo.save(follower);
        return new RedirectView(String.format("/user/%s", name));
    }

    @GetMapping("/user/{name}")
    public String userView(Model model, @PathVariable("name") String name) {
        model.addAttribute("user", userRepo.findByName(name));
        model.addAttribute("viewer", userRepo.findByName(SecurityContextHolder.getContext().getAuthentication().getName()));
        return "user";
    }

    @GetMapping("/user/{name}")
    public String userView(Model model, @PathVariable("name") String name) {
        model.addAttribute("user", userRepo.findByName(name));
        return "user";
    }
}
