package com.example.balancing.services;

import com.example.balancing.models.Record;

import java.util.List;

public interface RecordService {
    List<Record> getAllRecords();

    Record getRecordById(Long id);

    List<Record> getRecordsByMode(String mode);

    Record createRecord(Record record);

    Record updateRecord(Long id, Record record);

    void deleteRecord(Long id);

    List<Record> getAllCompleteRecords();

}
