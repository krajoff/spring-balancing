package com.example.balancing.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UnitNotFoundException extends RuntimeException {
    public UnitNotFoundException(String message) {
        super(message);
    }

    public UnitNotFoundException() {
        super("Агрегат не найден");
    }
}