package com.example.balancing.exception.point;

public class PointNotFoundException extends RuntimeException{
    public PointNotFoundException() {
        super("Место измерения вибрации не найдено");
    }
}
