package com.example.balancing.service.record;

import com.example.balancing.entity.Record;
import com.example.balancing.exception.EntityTypeException;
import com.example.balancing.exception.NotFoundElementException;
import com.example.balancing.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordRepository recordRepository;

    public Record getRecordById(UUID id) {
        return recordRepository.findById(id).orElseThrow(() -> new NotFoundElementException(EntityTypeException.RECORD));
    }

    public Record createRecord(Record record) {
        return recordRepository.save(record);
    }

    public Record updateRecord(UUID id, Record record) {
        Record existingRecord = getRecordById(id);
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

    public void deleteRecord(UUID id) {
        recordRepository.deleteById(id);
    }

}
