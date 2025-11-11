package com.example.balancing.entity.record;

import com.example.balancing.vo.complex.Complex;

public interface IRecord {
    Complex getComplexVibration();

    Complex getComplexSensitivity();

    Record getRecord();
}
