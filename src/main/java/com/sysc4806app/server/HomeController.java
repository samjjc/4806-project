package com.sysc4806app.server;

import com.sysc4806app.model.User;
import com.sysc4806app.repos.RoleRepo;
import com.sysc4806app.repos.UserRepo;
import com.sysc4806app.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class HomeController {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final ProductService productService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public HomeController(UserRepo userRepo, RoleRepo roleRepo,
                          ProductService productService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.productService = productService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("/")
    public String greeting(Model model) {
        model.addAttribute("products", productService.getNewestProducts(20));
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
            user.addRole(roleRepo.findByName("ROLE_USER"));
            userRepo.save(user);
            return new RedirectView("/login");

        }else{
            return new RedirectView("/signup?userexists");
        }

    }

    @GetMapping("/403")
    public String accessDenied() {
        return "error/accessDenied";
    }
}
