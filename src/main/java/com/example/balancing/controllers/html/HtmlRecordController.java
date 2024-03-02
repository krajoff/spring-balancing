package com.example.balancing.controllers.html;

import com.example.balancing.models.Record;
import com.example.balancing.services.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HtmlRecordController {
    @Autowired
    private RecordService recordService;

    @GetMapping("/record/all")
    public String getAllRecords(Model model) {
        model.addAttribute("record", recordService.getAllRecords());
        return "allrecords";
    }

    @PostMapping("/record/delete/{id}")
    public String deleteRecord(@PathVariable Long id) {
        recordService.deleteRecord(id);
        return "redirect:/record/all";
    }

    @PostMapping("/record/create")
    public String createRecord(Record record) {
        recordService.createRecord(record);
        return "redirect:/record/all";
    }

    @PostMapping("/record/edit/{id}")
    public String updateRecord(@PathVariable Long id, Record record) {
        recordService.updateRecord(id, record);
        return "redirect:/record/all";
    }

}
