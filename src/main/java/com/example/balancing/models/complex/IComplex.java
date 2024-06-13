package com.example.balancing.models.complex;

public interface IComplex {
    Double abs();

    Double phase();

    Complex plus(Complex that);

    Complex minus(Complex that);

    Complex times(Complex that);

    Complex scale(Double alpha);

    Complex conjugate();

    Complex reciprocal();

    Double re();

    Double im();

    Complex divides(Complex that);

    Complex exp();

    Complex sin();

    Complex cos();

    Complex tan();

    Complex shift(Double that);
}
