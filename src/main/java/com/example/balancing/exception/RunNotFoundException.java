package com.example.balancing.exception;

public class RunNotFoundException extends RuntimeException{
    public RunNotFoundException() {
        super("Пуск не найден");
    }
}
