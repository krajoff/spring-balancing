package com.example.balancing.exception.token;

public class AccessTokenExpiredException extends RuntimeException {
    public AccessTokenExpiredException(String message) {
        super(message);
    }

    public AccessTokenExpiredException() {
        this("Время жизни аксес-токен истекло");
    }
}
