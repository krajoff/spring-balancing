package com.example.balancing.models.builder;

import com.example.balancing.models.Complex;
import com.example.balancing.models.Record;

public interface RecordBuilder {
    public RecordBuilder mode(String mode);
    public RecordBuilder magvibration(Double magvibration);
    public RecordBuilder phasevibration(Double pvibration);
    public RecordBuilder complexVibration(Complex complexVibration);
    public Record build();
}
