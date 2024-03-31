package com.example.balancing.services.record;

import com.example.balancing.models.record.Record;
import com.example.balancing.models.unit.Unit;

import java.util.List;

public interface RecordService {
    List<Record> getAllRecords();

    Record getRecordById(Long id);

    List<Record> getRecordsByMode(String mode);

    List<Record> getRecordsByUnit(Unit unit);

    Record createRecord(Record record);

    Record updateRecord(Long id, Record record);

    void deleteRecord(Long id);

    Record correctRecord(Record record);

}
