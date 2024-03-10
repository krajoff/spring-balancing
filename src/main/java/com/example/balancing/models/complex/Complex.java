package com.example.balancing.models.complex;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Complex implements IComplex {
    @JsonProperty("real")
    private final Double re;
    @JsonProperty("imag")
    private final Double im;

    public Complex(Double real, Double imag) {
        re = real;
        im = imag;
    }
    public Double abs() {
        return Math.hypot(re, im);
    }

    public Double phase() {
        return Math.atan2(im, re);
    }

    public Complex plus(Complex that) {
        Double real = this.re + that.re;
        Double imag = this.im + that.im;
        return new Complex(real, imag);
    }

    public Complex minus(Complex that) {
        Double real = this.re - that.re;
        Double imag = this.im - that.im;
        return new Complex(real, imag);
    }

    public Complex times(Complex that) {
        Double real = this.re * that.re - this.im * that.im;
        Double imag = this.re * that.im + this.im * that.re;
        return new Complex(real, imag);
    }

    public Complex scale(Double alpha) {
        return new Complex(alpha * re, alpha * im);
    }

    public Complex conjugate() {
        return new Complex(re, -im);
    }

    public Complex reciprocal() {
        Double scale = re * re + im * im;
        return new Complex(re / scale, -im / scale);
    }

    public Double re() {
        return re;
    }

    public Double im() {
        return im;
    }

    public Complex divides(Complex that) {
        return this.times(that.reciprocal());
    }

    public Complex exp() {
        return new Complex(Math.exp(re) * Math.cos(im), Math.exp(re) * Math.sin(im));
    }

    public Complex sin() {
        return new Complex(Math.sin(re) * Math.cosh(im), Math.cos(re) * Math.sinh(im));
    }


    public Complex cos() {
        return new Complex(Math.cos(re) * Math.cosh(im), -Math.sin(re) * Math.sinh(im));
    }


    public Complex tan() {
        return sin().divides(cos());
    }

    public Complex shift(Double that) {
        Double z = this.abs();
        return new Complex(z * Math.cos(that + this.phase()),
                z * Math.sin(that + this.phase()));
    }

    public String toString(int precision) {
        if (precision != 0) {
            precision = Math.abs(precision);
        } else {
            precision = 1;
        }
        String format = "%." + precision + "f";
        if (im == 0) return String.format(format, re) + "";
        if (re == 0) return String.format(format, im) + "i";
        if (im < 0) return String.format(format, re) + "-" +
                String.format(format, -im) + "i";
        return String.format(format, re) + "+" +
                String.format(format, im) + "i";
    }

    public String toString() {
        return toString(0);
    }

}