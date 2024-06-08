package com.example.balancing.services.record;

import com.example.balancing.dto.RecordDto;
import com.example.balancing.models.record.Record;

import java.util.List;

public interface RecordService {
    List<RecordDto> getAllRecords();

    RecordDto getRecordById(Long id);

    List<RecordDto> getRecordsByMode(String mode);

    RecordDto createRecord(Record record);

    RecordDto updateRecord(Long id, Record record);

    void deleteRecord(Long id);

}
