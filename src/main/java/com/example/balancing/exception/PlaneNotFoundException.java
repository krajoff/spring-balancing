package com.example.balancing.exception;

public class PlaneNotFoundException extends RuntimeException {
    public PlaneNotFoundException() {
        super("Плоскоть установки грузов не найдена");
    }
}
