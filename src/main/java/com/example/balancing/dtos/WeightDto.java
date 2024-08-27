package com.example.balancing.dtos;

import com.example.balancing.models.complex.Complex;
import com.example.balancing.models.unit.Unit;
import lombok.Data;

import java.util.List;

/**
 * DTO весов, установленных или вычисленных для агрегата.
 * Содержит информацию о весе
 */
@Data
public class WeightDto {
    private Integer plane;
    private Integer numberRun;
    private Integer reference;
    private Double magRefWeight;
    private Double phaseRefWeight;
    private Complex complexRefWeight;
    private Double magWeight;
    private Double phaseWeight;
    private Complex complexWeight;
    private Unit unit;
    private List<RecordDto> records;
    private String systemInformation;
    private boolean isTarget;
}
