package com.example.balancing.utils;

import com.example.balancing.dtos.RecordDto;
import com.example.balancing.dtos.WeightDto;
import com.example.balancing.models.record.Record;
import com.example.balancing.models.weight.Weight;
import org.springframework.stereotype.Service;

@Service
public class MappingUtils {

    /**
     * Convert from entity to dto
     *
     * @return RecordDto
     */
    public RecordDto mapToRecordDto(Record record) {
        RecordDto dto = new RecordDto();
        dto.setId(record.getId());
        dto.setPlace(record.getPlace());
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
     *
     * @return Record
     */
    public Record mapToRecordEntity(RecordDto dto) {
        Record record = new Record();
        record.setId(dto.getId());
        record.setPlace(dto.getPlace());
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


    /**
     * Convert from entity to dto
     *
     * @return WeightDto
     */
    public WeightDto mapToWeightDto(Weight weight) {
        WeightDto dto = new WeightDto();
        dto.setId(weight.getId());
        dto.setPlane(weight.getPlane());
        dto.setNumberRun(weight.getNumberRun());
        dto.setReference(weight.getReference());
        dto.setMagRefWeight(weight.getMagRefWeight());
        dto.setPhaseRefWeight(weight.getPhaseRefWeight());
        dto.setComplexRefWeight(weight.getComplexRefWeight());
        dto.setMagWeight(weight.getMagWeight());
        dto.setPhaseWeight(weight.getPhaseWeight());
        dto.setComplexWeight(weight.getComplexWeight());
        dto.setUnit(weight.getUnit());
        dto.setRecords(weight.getRecords().stream().map(this::mapToRecordDto).toList());
        dto.setSystemInformation(weight.getSystemInformation());
        dto.setTarget(weight.isTarget());
        return dto;
    }

    /**
     * Convert from dto to entity
     *
     * @return Weight
     */
    public Weight mapToWeightEntity(WeightDto dto) {
        Weight weight = new Weight();
        weight.setId(dto.getId());
        weight.setPlane(dto.getPlane());
        weight.setNumberRun(dto.getNumberRun());
        weight.setReference(dto.getReference());
        weight.setMagRefWeight(dto.getMagRefWeight());
        weight.setPhaseRefWeight(dto.getPhaseRefWeight());
        weight.setComplexRefWeight(dto.getComplexRefWeight());
        weight.setMagWeight(dto.getMagWeight());
        weight.setPhaseWeight(dto.getPhaseWeight());
        weight.setComplexWeight(dto.getComplexWeight());
        weight.setUnit(dto.getUnit());
        weight.setRecords(dto.getRecords().stream().map(this::mapToRecordEntity).toList());
        weight.setSystemInformation(dto.getSystemInformation());
        weight.setTarget(dto.isTarget());
        return weight;
    }

}
