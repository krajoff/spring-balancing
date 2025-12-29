package com.example.balancing.service.record;

import com.example.balancing.entity.Record;

import java.util.UUID;

public interface RecordService {

    Record getRecordById(UUID id);

    Record createRecord(Record record);

    Record updateRecord(UUID id, Record record);

    void deleteRecord(UUID id);

}
