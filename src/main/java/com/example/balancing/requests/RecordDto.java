package com.example.balancing.dtos;

import com.example.balancing.models.complex.Complex;
import com.example.balancing.models.mode.Mode;
import com.example.balancing.models.place.Place;
import com.example.balancing.models.weight.Weight;
import lombok.Data;

@Data
public class RecordDto {
    private Long id;
    private Place place;
    private String name;
    private Mode mode;
    private Double magVibration;
    private Double phaseVibration;
    private Complex complexVibration;
    private Boolean stage;
    private Weight weight;
    private Boolean isManualSensitivity;
    private Double magSensitivity;
    private Double phaseSensitivity;
    private Complex complexSensitivity;
    private Weight targetWeight;
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
