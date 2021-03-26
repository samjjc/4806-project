package com.sysc4806app.server;

import com.sysc4806app.model.Review;
import com.sysc4806app.model.User;
import com.sysc4806app.repos.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class HomeController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/")
    public String greeting(Model model) {
        return "index";
    }

    // Login form
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/signup")
    public String signupView(Model model){
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping(value = "/signup")
    public RedirectView signup(@ModelAttribute User user) {
        if(user.getName()==null || user.getName().isEmpty() || user.getPassword()==null||user.getPassword().isEmpty()){
            return new RedirectView("/signup?error");
        }
        User userExists = userRepo.findByName(user.getName());
        if (userExists == null) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepo.save(user);
            return new RedirectView("/login");

        }else{
            return new RedirectView("/signup?userexists");
        }

    }
}
