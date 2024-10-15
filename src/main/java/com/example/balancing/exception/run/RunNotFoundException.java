package com.example.balancing.exception.run;

public class RunNotFoundException extends RuntimeException{
    public RunNotFoundException() {
        super("Пуск не найден");
    }
}
