package com.example.balancing.exception.weight;

public class WeightNotFoundException extends RuntimeException {
    public WeightNotFoundException() {
        super("Груз не найден.");
    }
}
