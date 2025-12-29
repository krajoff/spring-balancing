package com.example.balancing.service.record;

import com.example.balancing.entity.Record;

public interface RecordService {
    Record getRecordById(Long id);
    Record createRecord(Record record);
    Record updateRecord(Long id, Record record);
    void deleteRecord(Long id);
}
