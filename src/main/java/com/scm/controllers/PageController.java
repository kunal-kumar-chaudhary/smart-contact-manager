package com.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class PageController {

    @GetMapping("/home")
    public String getMethodName(Model model) {
        model.addAttribute("name", "kunal kumar is the new john doe");
        model.addAttribute("age", 20);
        return "home";
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "about";
    }
    
    @GetMapping("/services")
    public String servicesPage() {
        return "services";
    }
}
