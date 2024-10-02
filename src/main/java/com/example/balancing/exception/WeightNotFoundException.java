package com.example.balancing.exception;

public class WeightNotFoundException extends RuntimeException {
    public WeightNotFoundException() {
        super("Груз не найден.");
    }
}
