package com.example.balancing.controllers.web;

import com.example.balancing.dtos.user.UserDto;
import com.example.balancing.services.user.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
@Controller
@Tag(name = "Общая информация о пользователе")
@RequestMapping("/user")
public class UserProfileController {

    private final UserService userService;

    public UserProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getMainInformation(Model model) {
        UserDto user = userService.getCurrentUserDto();
        System.out.println("----->" + user);
        model.addAttribute("user", user);
        return "user/profile";
    }

}
