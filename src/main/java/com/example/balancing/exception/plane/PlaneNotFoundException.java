package com.example.balancing.exception.plane;

public class PlaneNotFoundException extends RuntimeException {
    public PlaneNotFoundException() {
        super("Плоскоть установки грузов не найдена");
    }
}
