package com.example.balancing.controllers.web;

import com.example.balancing.models.record.Record;
import com.example.balancing.services.record.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/record")
public class WebRecordController {
    @Autowired
    private RecordService recordService;

    @GetMapping("/all")
    public String getAllRecords(Model model) {
        model.addAttribute("records", recordService.getAllRecords());
        return "index-short";
    }

    @GetMapping("/index")
    public String getAllCompleteRecords(Model model) {
        model.addAttribute("records", recordService.getAllCompleteRecords());
        return "record/index";
    }

    @PostMapping("/delete/{id}")
    public String deleteRecord(@PathVariable Long id) {
        recordService.deleteRecord(id);
        return "redirect:/record/index";
    }

    @PostMapping("/create")
    public String createRecord(Record record) {
        recordService.createRecord(record);
        return "redirect:/record/index";
    }
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        model.addAttribute("record", recordService.getRecordById(id));
        return "record/update-record";
    }

    @PostMapping("/update/{id}")
    public String updateRecord(@PathVariable Long id, Record record) {
        recordService.updateRecord(id, record);
        return "redirect:/record/index";
    }

}
