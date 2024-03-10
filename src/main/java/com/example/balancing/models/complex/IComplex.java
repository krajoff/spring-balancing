package com.example.balancing.models.complex;

import com.example.balancing.models.complex.Complex;

public interface IComplex {
    public Double abs();

    public Double phase();

    public Complex plus(Complex that);

    public Complex minus(Complex that);

    public Complex times(Complex that);

    public Complex scale(Double alpha);

    public Complex conjugate();

    public Complex reciprocal();

    public Double re();

    public Double im();

    public Complex divides(Complex that);

    public Complex exp();

    public Complex sin();

    public Complex cos();

    public Complex tan();

    public Complex shift(Double that);
}
