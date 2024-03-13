package com.example.balancing.services.record;

import com.example.balancing.models.complex.Complex;
import com.example.balancing.models.record.Record;
import com.example.balancing.models.unit.Unit;
import com.example.balancing.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    private RecordRepository recordRepository;

    public List<Record> getAllRecords() {
        return recordRepository.findAll();
    }

    public Record getRecordById(Long id) {
        return recordRepository.findById(id).orElseThrow(() -> new RuntimeException("Record not found"));
    }

    public List<Record> getRecordsByMode(String mode) {
        return recordRepository.findByMode(mode);
    }

    public List<Record> getRecordsByUnit(Unit unit) {
        return recordRepository.findByUnit(unit);
    }

    public Record createRecord(Record record) {
        return recordRepository.save(record);
    }

    public Record updateRecord(Long id, Record record) {
        Record existingRecord = getRecordById(id);
        existingRecord.setStage(record.getStage());
        existingRecord.setMode(record.getMode());
        existingRecord.setMagweight(record.getMagweight());
        existingRecord.setPhaseweight(record.getPhaseweight());
        existingRecord.setMagvibration(record.getMagvibration());
        existingRecord.setPhasevibration(record.getPhasevibration());
        existingRecord.setReference(record.getReference());
        return recordRepository.save(existingRecord);
    }

    public void deleteRecord(Long id) {
        recordRepository.deleteById(id);
    }

    public List<Record> getAllCompleteRecords() {
        List<Record> records = getAllRecords();
        for (Record record : records) {
            Record tempRecord = record;
            Complex totalComplexWeight = record.getComplexWeight();
            Complex totalComplexVibration = record.getComplexVibration();
            while (tempRecord.getReference() != -1) {
                try {
                    totalComplexWeight = totalComplexWeight.
                            plus(getRecordById(tempRecord.getReference()).
                                    getComplexWeight());
                    totalComplexVibration = totalComplexVibration.
                            plus(getRecordById(tempRecord.getReference()).getComplexVibration());
                    tempRecord = getRecordById(tempRecord.getReference());
                } catch (RuntimeException e) {
                    tempRecord.setReference(-1L);
                    updateRecord(tempRecord.getId(), tempRecord);
                    System.out.println(e.getMessage());
                }
            }
            record.setComplexTotalWeight(totalComplexWeight);
            record.setComplexTotalVibration(totalComplexVibration);
        }
        Collections.sort(records, Comparator.comparingLong(Record::getId));
        return records;
    }

    public List<Record> getCompleteRecordsByUnit(List<Record> records) {
        for (Record record : records) {
            Record tempRecord = record;
            Complex totalComplexWeight = record.getComplexWeight();
            Complex totalComplexVibration = record.getComplexVibration();
            while (tempRecord.getReference() != -1) {
                try {
                    totalComplexWeight = totalComplexWeight.
                            plus(getRecordById(tempRecord.getReference()).
                                    getComplexWeight());
                    totalComplexVibration = totalComplexVibration.
                            plus(getRecordById(tempRecord.getReference()).getComplexVibration());
                    tempRecord = getRecordById(tempRecord.getReference());
                } catch (RuntimeException e) {
                    tempRecord.setReference(-1L);
                    updateRecord(tempRecord.getId(), tempRecord);
                    System.out.println(e.getMessage());
                }
            }
            record.setComplexTotalWeight(totalComplexWeight);
            record.setComplexTotalVibration(totalComplexVibration);
        }
        Collections.sort(records, Comparator.comparingLong(Record::getId));
        return records;
    }
}