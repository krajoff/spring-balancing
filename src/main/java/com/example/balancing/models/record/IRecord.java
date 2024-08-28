package com.example.balancing.models.record;

import com.example.balancing.models.complex.Complex;

public interface IRecord {
    Complex getComplexVibration();

    Complex getComplexSensitivity();

    Record getRecord();
}
