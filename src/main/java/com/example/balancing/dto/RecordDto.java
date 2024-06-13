package com.example.balancing.dto;

import com.example.balancing.models.complex.Complex;
import com.example.balancing.models.point.Point;
import com.example.balancing.models.weight.Weight;
import lombok.Data;

@Data
public class RecordDto {
    private Long id;
    private Point point;
    private String name;
    private String mode;
    private Double magVibration;
    private Double phaseVibration;
    private Complex complexVibration;
    private Boolean stage;
    private Weight weight;

    private Double magSensitivity;
    private Double phaseSensitivity;
    private Complex complexSensitivity;
    private Weight targetWeight;
    private Double magTargetWeight;
    private Double phaseTargetWeight;

    public Double getMagTargetWeight() {
        return this.targetWeight.getMagWeight();
    }

    public Double getPhaseTargetWeight() {
        return this.targetWeight.getPhaseWeight();
    }

    public RecordDto getRecord() {
        return this;
    }


}
