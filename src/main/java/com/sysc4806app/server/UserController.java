package com.sysc4806app.server;

import com.sysc4806app.exceptions.UserNotFoundException;
import com.sysc4806app.model.User;
import com.sysc4806app.repos.UserRepo;
import com.sysc4806app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;

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
    public String userView(Model model, @PathVariable("name") String name) throws Exception {
        User user =userRepo.findByName(name);
        if(user==null){
            throw new UserNotFoundException();
        }
        User viewer = userRepo.findByName(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("user",user );
        model.addAttribute("viewer", viewer);
        return "user";
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String noUserError(){
        return "error/noUserError";
    }

    @PreAuthorize("#name == authentication.name")
    @GetMapping(path="/user/{name}/following")
    public String requestFollowingPage(@PathVariable("name") String name, Model model) {
        model.addAttribute("topFollowedUsers", userService.getMostPopularUsers(10));
        model.addAttribute("topSimilarUsers", userService.getMostSimilarUsers(SecurityContextHolder.getContext().getAuthentication().getName(), 10));
        model.addAttribute("following", userService.getFollowing(name));
        return "following";
    }
}
