package com.example.balancing.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class WebMainController {
    @GetMapping({"", "/"})
    String getUnitsPage() {
        return "redirect:unit/index";
    }
}
