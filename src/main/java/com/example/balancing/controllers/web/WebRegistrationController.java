package com.example.balancing.controllers.web;

import com.example.balancing.models.user.User;
import com.example.balancing.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class WebRegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam(required = true, defaultValue = "!@") String username,
                               @RequestParam(required = true, defaultValue = "!@") String password) {
        if (!(username.equals("!@") & password.equals("!@"))) {
            System.out.println(username + " " + password);
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            userService.saveUser(user);
        }
        return "redirect:/unit/index";
    }

    @GetMapping("/login")
    String login() {
        return "login";
    }
}
