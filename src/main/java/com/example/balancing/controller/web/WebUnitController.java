package com.example.balancing.controller.web;

import com.example.balancing.entity.user.User;
import com.example.balancing.service.unit.UnitService;
import com.example.balancing.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/station/{stationId}")
@RequiredArgsConstructor
public class WebUnitController {

    private final UnitService unitService;
    private final UserService userService;

    @GetMapping
    public String listUnits(@PathVariable("stationId") UUID stationId, Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);
        model.addAttribute("units", unitService.getByStation(stationId));
        return "units/index";
    }

}


