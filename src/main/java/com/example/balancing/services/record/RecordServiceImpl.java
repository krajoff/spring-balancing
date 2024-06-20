package com.example.balancing.services.record;

import com.example.balancing.dto.RecordDto;
import com.example.balancing.models.record.Record;
import com.example.balancing.repository.RecordRepository;
import com.example.balancing.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private MappingUtils mappingUtils;

    public List<RecordDto> getAllRecords() {
        return recordRepository.findAll().stream()
                .map(mappingUtils::mapToRecordDto)
                .collect(Collectors.toList());
    }

    public RecordDto getRecordById(Long id) {
        return mappingUtils.mapToRecordDto(recordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found")));
    }

    public List<RecordDto> getRecordsByMode(String mode) {
        return recordRepository.findByMode(mode).stream()
                .map(mappingUtils::mapToRecordDto)
                .collect(Collectors.toList());
    }

    public RecordDto createRecord(Record record) {
        return mappingUtils.mapToRecordDto(recordRepository.save(record));
    }

    public RecordDto updateRecord(Long id, RecordDto recordDto) {
        Record existingRecord = mappingUtils.mapToRecordEntity(getRecordById(id));
        existingRecord.setMode(record.getMode());
        existingRecord.setPlace(record.getPlace());
        existingRecord.setMagVibration(record.getMagVibration());
        existingRecord.setPhaseVibration(record.getPhaseVibration());
        existingRecord.setStage(record.getStage());
        if (record.getIsManualSensitivity()) {
            existingRecord.setMagSensitivity(record.getMagSensitivity());
            existingRecord.setPhaseSensitivity(record.getPhaseSensitivity());
        }
        return mappingUtils.mapToRecordDto(recordRepository.save(existingRecord));
    }

    public void deleteRecord(Long id) {
        recordRepository.deleteById(id);
    }

}