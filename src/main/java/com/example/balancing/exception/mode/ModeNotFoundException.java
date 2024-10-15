package com.example.balancing.exception.mode;

public class ModeNotFoundException extends RuntimeException{
    public ModeNotFoundException() {
        super("Режим работы агрегата не найден.");
    }
}
