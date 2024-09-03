package com.example.balancing.controllers.api;

import com.example.balancing.dtos.record.RecordDto;
import com.example.balancing.models.record.Record;
import com.example.balancing.services.record.RecordService;
import com.example.balancing.utils.MappingUtils;
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

    @Autowired
    private MappingUtils mappingUtils;

    @GetMapping
    public List<RecordDto> getAllCompleteRecords() {
        return recordService.getAllRecords()
                .stream().map(mappingUtils::mapToRecordDto).toList();
    }

    @PostMapping
    public RecordDto createRecord(@RequestBody Record record) {
        return mappingUtils.mapToRecordDto(recordService.createRecord(record));
    }

    @GetMapping("/{record_id}")
    public RecordDto getRecordById(@PathVariable Long id) {
        return mappingUtils.mapToRecordDto(recordService.getRecordById(id));
    }

    @PutMapping("/{record_id}")
    public RecordDto updateRecord(@PathVariable Long id, @RequestBody Record record) {
        return mappingUtils.mapToRecordDto(recordService.updateRecord(id, record));
    }

    @DeleteMapping("/{record_id}")
    public void deleteRecord(@PathVariable Long id) {
        recordService.deleteRecord(id);
    }

}
