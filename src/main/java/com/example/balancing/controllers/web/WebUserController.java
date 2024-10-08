package com.example.balancing.controllers.web;

import com.example.balancing.models.user.User;
import com.example.balancing.services.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class WebUserController {
    static final Logger logger = LoggerFactory.getLogger(SpringBootApplication.class);

    @Autowired
    private UserService userService;

    @GetMapping("/index")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "index";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/user/index";
    }

    @PostMapping("/create")
    public String createUser(User user) {
        userService.createUser(user);
        return "redirect:/user/index";
    }
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "update-user";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable Long id, User user) {
        userService.updateUser(id, user);
        return "redirect:/user/index";
    }

}
