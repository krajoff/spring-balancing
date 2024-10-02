package com.example.balancing.services.record;

import com.example.balancing.exception.RecordNotFoundException;
import com.example.balancing.models.record.Record;
import com.example.balancing.repositories.record.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordRepository recordRepository;

    public Record getRecordById(Long id) {
        return recordRepository.findById(id)
                .orElseThrow(RecordNotFoundException::new);
    }

    public Record createRecord(Record record) {
        return recordRepository.save(record);
    }

    public Record updateRecord(Long id, Record record) {
        Record existingRecord = getRecordById(id);
        existingRecord.setMode(record.getMode());
        existingRecord.setPoint(record.getPoint());
        existingRecord.setIsUsed(record.getIsUsed());
        if (record.getIsManualSensitivity()) {
            existingRecord.setIsManualSensitivity(Boolean.TRUE);
            existingRecord.setMagSensitivity(record.getMagSensitivity());
            existingRecord.setPhaseSensitivity(record.getPhaseSensitivity());
        } else {
            existingRecord.setMagVibration(record.getMagVibration());
            existingRecord.setPhaseVibration(record.getPhaseVibration());
        }
        return createRecord(existingRecord);
    }

    public void deleteRecord(Long id) {
        recordRepository.deleteById(id);
    }

}