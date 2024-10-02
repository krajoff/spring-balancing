package com.example.balancing.exception;

public class RefreshTokenNotFoundException extends RuntimeException {
    public RefreshTokenNotFoundException() {
        super("Рефреш-токен не найден.");
    }
}
