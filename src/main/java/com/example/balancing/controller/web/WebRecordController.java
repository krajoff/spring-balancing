package com.example.balancing.controller.web;

import com.example.balancing.entity.user.User;
import com.example.balancing.service.record.RecordService;
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
@RequiredArgsConstructor
@RequestMapping("/station/{stationId}/unit/{unitId}")
public class WebRecordController {

    private final RecordService recordService;
    private final UserService userService;
    private final UnitService unitService;


    @GetMapping
    public String listRecords(@PathVariable("stationId") UUID stationId,
                              @PathVariable("unitId") UUID unitId,
                              Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);
        model.addAttribute("unit", unitService.getById(unitId));
        model.addAttribute("record", recordService.getByUnit(unitId));
        return "records/index";
    }

}
