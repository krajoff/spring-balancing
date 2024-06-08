package com.example.balancing.utils;

import com.example.balancing.dto.RecordDto;
import com.example.balancing.models.record.Record;
import org.springframework.stereotype.Service;

@Service
public class MappingUtils {

    /**
     * Convert from entity to dto
     * @return RecordDto
     */
    public RecordDto mapToRecordDto(Record record) {
        RecordDto dto = new RecordDto();
        dto.setId(record.getId());
        dto.setPointId(record.getPointId());
        dto.setMode(record.getMode());
        dto.setMagVibration(record.getMagVibration());
        dto.setPhaseVibration(record.getPhaseVibration());
        dto.setStage(record.getStage());
        dto.setWeight(record.getWeight());
        dto.setComplexVibration(record.getComplexVibration());
        dto.setMagSensitivity(record.getMagSensitivity());
        dto.setPhaseSensitivity(record.getPhaseSensitivity());
        dto.setComplexSensitivity(record.getComplexSensitivity());
        return dto;
    }

    /**
     * Convert from dto to entity
     * @return Record
     */
    public Record mapToRecordEntity(RecordDto dto) {
        Record record = new Record();
        record.setId(dto.getId());
        record.setPointId(dto.getPointId());
        record.setMode(dto.getMode());
        record.setMagVibration(dto.getMagVibration());
        record.setPhaseVibration(dto.getPhaseVibration());
        record.setStage(dto.getStage());
        record.setWeight(dto.getWeight());
        record.setComplexVibration(dto.getComplexVibration());
        record.setMagSensitivity(dto.getMagSensitivity());
        record.setPhaseSensitivity(dto.getPhaseSensitivity());
        record.setComplexSensitivity(dto.getComplexSensitivity());
        return record;
    }

}
