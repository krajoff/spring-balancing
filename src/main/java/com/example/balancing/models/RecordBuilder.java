package com.example.balancing.models;

public interface RecordBuilder {
    public RecordBuilder mode(String mode);
    public RecordBuilder magvibration(Double magvibration);
    public RecordBuilder complexVibration(Complex complexVibration);
    public Record build();
}
