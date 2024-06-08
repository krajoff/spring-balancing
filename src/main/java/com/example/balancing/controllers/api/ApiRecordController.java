package com.example.balancing.controllers.api;

import com.example.balancing.dto.RecordDto;
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
    public List<RecordDto> getAllCompleteRecords() {
        return recordService.getAllRecords();
    }

    @PostMapping
    public RecordDto createRecord(@RequestBody Record record) {
        return recordService.createRecord(record);
    }

    @GetMapping("/{record_id}")
    public RecordDto getRecordById(@PathVariable Long id) {
        return recordService.getRecordById(id);
    }

    @PutMapping("/{record_id}")
    public RecordDto updateRecord(@PathVariable Long id, @RequestBody Record record) {
        return recordService.updateRecord(id, record);
    }

    @DeleteMapping("/{record_id}")
    public void deleteRecord(@PathVariable Long id) {
        recordService.deleteRecord(id);
    }

}
