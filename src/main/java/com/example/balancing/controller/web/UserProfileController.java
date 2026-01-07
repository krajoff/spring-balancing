package com.example.balancing.controller.web;

import com.example.balancing.dto.UserDto;
import com.example.balancing.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserProfileController {

    private final UserService userService;

    public UserProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getMainInformation(Model model) {
        UserDto user = userService.getCurrentUserDto();
        model.addAttribute("user", user);
        return "user/profile";
    }

}
