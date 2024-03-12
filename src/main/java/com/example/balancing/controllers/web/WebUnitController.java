package com.example.balancing.controllers.web;

import com.example.balancing.models.record.Record;
import com.example.balancing.models.unit.Unit;
import com.example.balancing.services.record.RecordService;
import com.example.balancing.services.unit.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/index")
    public String getAllUnits(Model model) {
        model.addAttribute("units", unitService.getAllUnits());
        return "unit/index";
    }

    @PostMapping("/delete/{id}")
    public String deleteUnit(@PathVariable Long id) {
        unitService.deleteUnit(id);
        return "redirect:/unit/index";
    }

    @PostMapping("/create")
    public String createUnit(Unit unit) {
        unitService.createUnit(unit);
        return "redirect:/unit/index";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        model.addAttribute("unit", unitService.getUnitById(id));
        return "unit/update-unit";
    }

    @PostMapping("/update/{id}")
    public String updateUnit(@PathVariable Long id, Unit unit) {
        unitService.updateUnit(id, unit);
        return "redirect:/unit/index";
    }

    @GetMapping("/{id}/records")
    public String getRecordsByUnit(@PathVariable Long id, Model model) {
        model.addAttribute("unit", unitService.getUnitById(id));
        return "unit/unit-records";
    }

    @PostMapping("/{id}/record/create")
    public String addRecord(@PathVariable Long id, Record record) {
        unitService.addRecord(id, record);
        return "unit/{id}/records";
    }
}
