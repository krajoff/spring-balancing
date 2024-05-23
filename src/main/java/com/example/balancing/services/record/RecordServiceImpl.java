package com.example.balancing.services.record;

import com.example.balancing.models.record.Record;
import com.example.balancing.models.unit.Unit;
import com.example.balancing.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordRepository recordRepository;

    public List<Record> getAllRecords() {
        return recordRepository.findAll();
    }

    public Record getRecordById(Long id) {
        return recordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));
    }

    public List<Record> getRecordsByMode(String mode) {
        return recordRepository.findByMode(mode);
    }

    public Record createRecord(Record record) {
        return recordRepository.save(record);
    }

    public Record updateRecord(Long id, Record record) {
        Record existingRecord = getRecordById(id);
        existingRecord.setMode(record.getMode());
        existingRecord.setPlace(record.getPlace());
        existingRecord.setMagVibration(record.getMagVibration());
        existingRecord.setPhaseVibration(record.getPhaseVibration());
        existingRecord.setStage(record.getStage());
        return recordRepository.save(existingRecord);
    }

    public void deleteRecord(Long id) {
        recordRepository.deleteById(id);
    }

}