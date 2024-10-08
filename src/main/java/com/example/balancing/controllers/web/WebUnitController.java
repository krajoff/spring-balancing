package com.example.balancing.controllers.web;

import com.example.balancing.exception.UnitNotFoundException;
import com.example.balancing.models.unit.Unit;
import com.example.balancing.models.user.User;
import com.example.balancing.services.record.RecordService;
import com.example.balancing.services.unit.UnitService;
import com.example.balancing.services.user.UserService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class WebUnitController {
    @Autowired
    private UnitService unitService;

    @Autowired
    private RecordService recordService;

    private final UserService userService;

    @GetMapping({"/", "", "/index"})
    public String getAllUnits(Model model) {
        Authentication authentication = SecurityContextHolder
                .getContext().getAuthentication();
        User user = userService.getUserByUsername(authentication.getName());
        model.addAttribute("username", authentication.getName());
        model.addAttribute("units", unitService.getUnitsByUserId(user.getId()));
        return "unit/index";
    }

    @GetMapping("/delete/{unit_id}")
    public String showDeleteForm(@PathVariable Long unit_id, Model model) {
        Authentication authentication = SecurityContextHolder
                .getContext().getAuthentication();
        User user = userService.getUserByUsername(authentication.getName());
        model.addAttribute("username", authentication.getName());
        model.addAttribute("unit", unitService.getUnitById(unit_id));
        return "unit/delete";
    }

    @PostMapping("/delete/{unit_id}")
    public String deleteUnit(@PathVariable Long unit_id, Long unit_confirmation) {
        try {
            if (unit_confirmation.equals(unit_id)) {
                try {
                    unitService.deleteUnit(unit_id);
                } catch (UnitNotFoundException e) {
                    return "Unit not found";
                }
            }
        } catch (NullPointerException e) {
            throw new UnitNotFoundException("NullPointerEexception");
        }
        return "redirect:/unit/";
    }


    @PostMapping("/create")
    public String createUnit(Unit unit) {
        Authentication authentication = SecurityContextHolder
                .getContext().getAuthentication();
        User user = userService.getUserByUsername(authentication.getName());
        unit.setUser(user);
        unitService.createUnit(unit);
        return "redirect:/unit/";
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
            return "redirect:/unit/";
        } catch (UnitNotFoundException e) {
            return "Unit not found";
        }
    }

    @GetMapping("/calculate/{unit_id}")
    public String calculateTarget(@PathVariable Long unit_id, Model model) {
        return "unit/calculate";
    }
}
