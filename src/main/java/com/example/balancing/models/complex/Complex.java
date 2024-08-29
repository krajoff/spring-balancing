package com.example.balancing.models.complex;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Класс, представляющий комплексное число. Содержит методы для выполнения
 * различных операций над комплексными числами, таких как сложение, вычитание,
 * умножение, деление и вычисление модуля и фазы.
 */
public class Complex implements IComplex {
    @JsonProperty("real")
    private final Double re;
    @JsonProperty("imag")
    private final Double im;

    /**
     * Конструктор для создания комплексного числа.
     *
     * @param real Реальная часть комплексного числа.
     * @param imag Мнимая часть комплексного числа.
     */
    public Complex(Double real, Double imag) {
        re = real;
        im = imag;
    }

    /**
     * Возвращает модуль (абсолютное значение) комплексного числа.
     *
     * @return Модуль комплексного числа.
     */
    public Double abs() {
        return Math.hypot(re, im);
    }

    /**
     * Возвращает фазу (аргумент) комплексного числа.
     *
     * @return Фаза комплексного числа в радианах.
     */
    public Double phase() {
        return Math.atan2(im, re);
    }

    /**
     * Выполняет сложение текущего комплексного числа с другим.
     *
     * @param that Другое комплексное число.
     * @return Результат сложения.
     */
    public Complex plus(Complex that) {
        Double real = this.re + that.re;
        Double imag = this.im + that.im;
        return new Complex(real, imag);
    }

    /**
     * Выполняет вычитание другого комплексного числа из текущего.
     *
     * @param that Другое комплексное число.
     * @return Результат вычитания.
     */
    public Complex minus(Complex that) {
        Double real = this.re - that.re;
        Double imag = this.im - that.im;
        return new Complex(real, imag);
    }

    /**
     * Выполняет умножение текущего комплексного числа на другое.
     *
     * @param that Другое комплексное число.
     * @return Результат умножения.
     */
    public Complex times(Complex that) {
        Double real = this.re * that.re - this.im * that.im;
        Double imag = this.re * that.im + this.im * that.re;
        return new Complex(real, imag);
    }

    /**
     * Масштабирует (уменьшает или увеличивает) комплексное число
     * на заданный коэффициент.
     *
     * @param alpha Коэффициент масштабирования.
     * @return Масштабированное комплексное число.
     */
    public Complex scale(Double alpha) {
        return new Complex(alpha * re, alpha * im);
    }

    /**
     * Возвращает сопряженное комплексное число.
     *
     * @return Сопряженное комплексное число.
     */
    public Complex conjugate() {
        return new Complex(re, -im);
    }

    /**
     * Возвращает обратное (рекуперационное) комплексное число.
     *
     * @return Обратное комплексное число.
     */
    public Complex reciprocal() {
        Double scale = re * re + im * im;
        return new Complex(re / scale, -im / scale);
    }

    /**
     * Возвращает реальную часть комплексного числа.
     *
     * @return Реальная часть.
     */
    public Double re() {
        return re;
    }

    /**
     * Возвращает мнимую часть комплексного числа.
     *
     * @return Мнимая часть.
     */
    public Double im() {
        return im;
    }

    /**
     * Выполняет деление текущего комплексного числа на другое.
     *
     * @param that Другое комплексное число.
     * @return Результат деления.
     */
    public Complex divides(Complex that) {
        return this.times(that.reciprocal());
    }

    /**
     * Возвращает экспоненциальное значение комплексного числа.
     *
     * @return Экспоненциальное комплексное число.
     */
    public Complex exp() {
        return new Complex(Math.exp(re) * Math.cos(im), Math.exp(re) * Math.sin(im));
    }

    /**
     * Возвращает синус комплексного числа.
     *
     * @return Синус комплексного числа.
     */
    public Complex sin() {
        return new Complex(Math.sin(re) * Math.cosh(im), Math.cos(re) * Math.sinh(im));
    }

    /**
     * Возвращает косинус комплексного числа.
     *
     * @return Косинус комплексного числа.
     */
    public Complex cos() {
        return new Complex(Math.cos(re) * Math.cosh(im), -Math.sin(re) * Math.sinh(im));
    }

    /**
     * Возвращает тангенс комплексного числа.
     *
     * @return Тангенс комплексного числа.
     */
    public Complex tan() {
        return sin().divides(cos());
    }

    /**
     * Выполняет фазовый сдвиг на заданное значение.
     *
     * @param that Значение сдвига.
     * @return Результат сдвига.
     */
    public Complex shift(Double that) {
        Double z = this.abs();
        return new Complex(z * Math.cos(that + this.phase()),
                z * Math.sin(that + this.phase()));
    }

    /**
     * Возвращает строковое представление комплексного числа с заданной точностью.
     *
     * @param precision Количество знаков после запятой.
     * @return Строковое представление комплексного числа.
     */
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