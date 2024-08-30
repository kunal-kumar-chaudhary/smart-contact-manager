package com.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.scm.forms.UserForm;

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

    @GetMapping("/contact")
    public String contactPage() {
        return "contact";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String signupPage(Model model) {
        // automatically form will be sent to the user page
        UserForm userForm = new UserForm();
        userForm.setName("kunal"); // setName setter coming from lombok
        model.addAttribute("userForm", userForm);
        return "register";
    }

    @PostMapping("/do-register")
    public String registerUser(@ModelAttribute UserForm userForm) {
        System.out.println(userForm);        
        // form validation
        
        return "redirect:/register";
    }

}
