package com.example.balancing.controllers.api;

import com.example.balancing.models.record.Record;
import com.example.balancing.services.record.RecordService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Record", description = "The Record API")
@RestController
@RequestMapping("/api/records")
public class ApiRecordController {
    @Autowired
    private RecordService recordService;

    @GetMapping
    public List<Record> getAllCompleteRecords() {
        return recordService.getAllCompleteRecords();
    }

    @PostMapping
    public Record createRecord(@RequestBody Record record) {
        return recordService.createRecord(record);
    }

    @GetMapping("/{id}")
    public Record getRecordById(@PathVariable Long id) {
        return recordService.getRecordById(id);
    }

    @PutMapping("/{id}")
    public Record updateRecord(@PathVariable Long id, @RequestBody Record record) {
        return recordService.updateRecord(id, record);
    }

    @DeleteMapping("/{id}")
    public void deleteRecord(@PathVariable Long id) {
        recordService.deleteRecord(id);
    }

}
