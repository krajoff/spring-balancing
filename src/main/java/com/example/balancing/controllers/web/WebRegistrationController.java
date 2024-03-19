package com.example.balancing.controllers.web;

import com.example.balancing.models.user.User;
import com.example.balancing.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping
public class WebRegistrationController {
    @Autowired
    private UserService userService;

    private Boolean isExist = false;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("isExist", isExist);
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password) {
        if (userService.getUserByUsername(username) == null) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            userService.saveUser(user);
            return "redirect:/login";
        }
        isExist = true;
        return "redirect:/register";
    }

    @GetMapping("/login")
    String login() {
        return "auth/login";
    }
}
