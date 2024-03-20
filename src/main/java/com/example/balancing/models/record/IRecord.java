package com.example.balancing.models.record;

import com.example.balancing.models.complex.Complex;

public interface IRecord {
    public Complex getComplexVibration();

    public Complex getComplexWeight();

    public Double getMagTotalWeight();

    public Double getPhaseTotalWeight();

    public Double getMagTotalVibration();

    public Double getPhaseTotalVibration();

}
