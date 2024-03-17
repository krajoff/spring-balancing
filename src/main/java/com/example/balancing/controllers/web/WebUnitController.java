package com.example.balancing.controllers.web;

import com.example.balancing.exception.UnitNotFoundException;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/unit")
public class WebUnitController {
    @Autowired
    private UnitService unitService;

    @Autowired
    private RecordService recordService;

    @Autowired
    private UserService userService;

    @GetMapping("/index")
    public String getAllUnits(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(authentication.getName());
        model.addAttribute("units", unitService.getUnitsByUserId(user.getId()));
        model.addAttribute("username", authentication.getName());
        return "unit/index";
    }

    @PostMapping("/delete/{unit_id}")
    public String deleteUnit(@PathVariable Long unit_id) {
        try {
            unitService.deleteUnit(unit_id);
        } catch (UnitNotFoundException e) {
            return "Unit not found";
        }
        return "redirect:/unit/index";
    }

    @PostMapping("/create")
    public String createUnit(Unit unit) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(authentication.getName());
        unit.setUser(user);
        unitService.createUnit(unit);
        return "redirect:/unit/index";
    }

    @GetMapping("/edit/{unit_id}")
    public String showUpdateForm(@PathVariable Long unit_id, Model model) {
        model.addAttribute("unit", unitService.getUnitById(unit_id));
        return "unit/update-unit";
    }

    @PostMapping("/update/{unit_id}")
    public String updateUnit(@PathVariable Long unit_id, Unit unit) {
        try {
            unitService.updateUnit(unit_id, unit);
            return "redirect:/unit/index";
        } catch (UnitNotFoundException e) {
            return "Unit not found";
        }
    }

    @GetMapping("/{unit_id}/records")
    public String getRecordsByUnit(@PathVariable Long unit_id, Model model) {
        model.addAttribute("unit", unitService.getCompleteUnitById(unit_id));
        return "unit/unit-records";
    }

    @PostMapping("/{unit_id}/target")
    public String calculateTargetWeight(@PathVariable Long unit_id) {
        unitService.getCompleteUnitById(unit_id);
        return "redirect:/unit/index";
    }


    @PostMapping("/{unit_id}/record/create")
    public String addRecord(@PathVariable Long unit_id, Record record) {
        unitService.addRecord(unit_id, record);
        return "redirect:/unit/{unit_id}/records";
    }

    @PostMapping("/{unit_id}/record/delete/{record_id}")
    public String deleteRecord(@PathVariable Long unit_id, @PathVariable Long record_id) {
        recordService.deleteRecord(record_id);
        return "redirect:/unit/{unit_id}/records";
    }

}
