package com.example.balancing.exception.token;

public class RefreshTokenNotFoundException extends RuntimeException {
    public RefreshTokenNotFoundException() {
        super("Рефреш-токен не найден.");
    }
}
