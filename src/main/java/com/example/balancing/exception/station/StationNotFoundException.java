package com.example.balancing.exception.station;

public class StationNotFoundException extends RuntimeException {
    public StationNotFoundException(String message) {
        super(message);
        
    }
}
