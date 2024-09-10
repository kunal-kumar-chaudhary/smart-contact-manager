package com.scm.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.services.impl.UserServiceImpl;

import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;

@Controller
public class PageController {

    private UserServiceImpl userService;

    // constructor injection
    private PageController(UserServiceImpl uServiceImpl) {
        this.userService = uServiceImpl;
    }

    @GetMapping("/")
    public String homePage() {
        return "redirect:/home";
    }

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

        // saving the user to database

        return "register";
    }

    // ModelAttribute -> it will bind the form data to the UserForm object
    // valid -> it will validate the form data
    // BindingResult -> it will store the validation result
    @PostMapping("/do-register")
    public String registerUser(@Valid @ModelAttribute UserForm userForm, BindingResult rBindingResult, HttpSession session) {
        System.out.println(userForm);
        // form validation

        if (rBindingResult.hasErrors()){
            return "register";
        }

        // save user to database -> by creating user service

        // note - User.builder().build() will create a new user object with default
        // values
        // User user = User.builder()
        //         .name(userForm.getName())
        //         .email(userForm.getEmail())
        //         .password(userForm.getPassword())
        //         .phoneNumber(userForm.getPhoneNumber())
        //         .about(userForm.getAbout())
        //         .profilePic("https://www.youtube.com/@Kunalgam456")
        //         .build();

        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setAbout(userForm.getAbout());
        user.setProfilePic("https://www.youtube.com/@Kunalgam456");

        // saving the user
        User savedUser = userService.saveUser(user);
        Message message = Message.builder().content("Registration successful").type(MessageType.green).build();
        session.setAttribute("message", message);
        System.out.println(session.getAttribute("message"));
        return "redirect:/register";
    }

}
