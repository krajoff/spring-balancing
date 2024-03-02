package com.example.balancing.services;

import com.example.balancing.models.Record;
import com.example.balancing.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    private RecordRepository recordRepository;

    @Override
    public List<Record> getAllRecords() {
        return recordRepository.findAll();
    }

    @Override
    public Record getRecordById(Long id) {
        return recordRepository.findById(id).orElseThrow(() -> new RuntimeException("Record not found"));
    }

    @Override
    public List<Record> getRecordsByMode(String mode) {
        return recordRepository.findByMode(mode);
    }

    @Override
    public Record createRecord(Record record) {
        return recordRepository.save(record);
    }

    @Override
    public Record updateRecord(Long id, Record record) {
        Record existingRecord = getRecordById(id);
        existingRecord.setStage(record.isStage());
        existingRecord.setMode(record.getMode());
        existingRecord.setMagweight(record.getMagweight());
        existingRecord.setPhaseweight(record.getPhaseweight());
        existingRecord.setMagvibration(record.getMagvibration());
        existingRecord.setPhasevibration(record.getPhasevibration());
        existingRecord.setReference(record.getReference());
        return recordRepository.save(existingRecord);
    }

    @Override
    public void deleteRecord(Long id) {
        recordRepository.deleteById(id);
    }
}
