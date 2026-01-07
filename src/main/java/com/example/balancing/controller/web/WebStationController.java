package com.example.balancing.controller.web;

import com.example.balancing.entity.user.User;
import com.example.balancing.service.station.StationService;
import com.example.balancing.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/stations")
@RequiredArgsConstructor
public class WebStationController {

    private final StationService stationService;
    private final UserService userService;

    @GetMapping
    public String listStations(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);
        model.addAttribute("stations", stationService.getAllByUser());
        return "stations/index";
    }

}

