package com.sysc4806app.server;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExampleController {
    @GetMapping("/")
    public String greeting(Model model) {
        return "index";
    }
}
