package com.example.balancing.models.builder;

import com.example.balancing.models.Complex;
import com.example.balancing.models.Record;
import com.example.balancing.services.RecordService;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;

public class FullRecordBuilder implements RecordBuilder {
    private String mode;
    private Double magvibration;
    private Double phasevibration;
    private Complex complexVibration;
    private Double magweight;
    private Double phaseweight;
    private Long reference;
    private boolean stage;

    public FullRecordBuilder() {
        super();
    }

    @Autowired
    private RecordService recordService;


    @Override
    public RecordBuilder mode(String mode) {
        this.mode = mode;
        return this;
    }

    @Override
    public RecordBuilder magvibration(Double magvibration) {
        this.magvibration = magvibration;
        return this;
    }

    @Override
    public RecordBuilder phasevibration(Double phasevibration) {
        this.phasevibration = phasevibration;
        return this;
    }


    @Override
    public RecordBuilder complexVibration(Complex complexVibration) {
        this.complexVibration = complexVibration;
        return this;
    }

    @Override
    public Record build() {
        return null;
    }
}
