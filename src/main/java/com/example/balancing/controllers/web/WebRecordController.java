package com.example.balancing.controllers.web;

import com.example.balancing.dtos.record.RecordDto;
import com.example.balancing.models.record.Record;
import com.example.balancing.models.unit.Unit;
import com.example.balancing.models.user.User;
import com.example.balancing.services.record.RecordService;
import com.example.balancing.services.unit.UnitService;
import com.example.balancing.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/unit/{unit_id}/record")
public class WebRecordController {
    @Autowired
    private RecordService recordService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private UserService userService;

    @GetMapping({"/", ""})
    public String getRecordsByUnit(@PathVariable Long unit_id, Model model) {
        Authentication authentication = SecurityContextHolder
                .getContext().getAuthentication();
        User user = userService.getUserByUsername(authentication.getName());
        model.addAttribute("username", authentication.getName());
        model.addAttribute("unit", unitService
                .calculateTargetWeight(unit_id));
        return "record/index";
    }

    @PostMapping("/create")
    public String createRecord(@PathVariable Long unit_id, RecordDto record) {
        Unit unit = unitService.getUnitById(unit_id);
        Long countRecord = unit.getRecordcount() + 1;
        unit.setRecordcount(countRecord);
        unitService.updateUnit(unit_id, unit);
        record.setUnit(unitService.getUnitById(unit_id));
        record.setInsideid(countRecord);
        recordService.createRecord(record);
        return "redirect:/unit/{unit_id}/record";
    }

    @GetMapping("/edit/{record_id}")
    public String showUpdateForm(@PathVariable Long unit_id,
                                 @PathVariable Long record_id,
                                 Model model) {
        model.addAttribute("record", recordService
                .getRecordById(record_id));
        model.addAttribute("unit", unitService
                .getUnitById(unit_id));
        return "record/index";
    }

    @PostMapping("/update/{record_id}")
    public String updateRecord(@PathVariable Long record_id, Record record) {
        recordService.updateRecord(record_id, record);
        return "redirect:/unit/{unit_id}/record";
    }

    @PostMapping("/delete/{record_id}")
    public String deleteRecord(@PathVariable Long unit_id,
                               @PathVariable Long record_id) {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        User user = userService.getUserByUsername(authentication.getName());
        if (unitService.getUnitById(unit_id).getUser().equals(user)) {
            recordService.deleteRecord(record_id);
        }
        return "redirect:/unit/{unit_id}/record";
    }
}
