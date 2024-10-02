package com.example.balancing.exception;

public class PointNotFoundException extends RuntimeException{
    public PointNotFoundException() {
        super("Место измерения вибрации не найдено");
    }
}
