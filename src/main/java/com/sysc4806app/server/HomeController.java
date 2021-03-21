package com.sysc4806app.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @GetMapping("/")
    public String greeting(Model model) {
        return "index";
    }

    // Login form
    @RequestMapping("/login")
    public String login(){
        return "login";
    }
}
