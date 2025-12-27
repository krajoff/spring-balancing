package com.example.balancing.exception.auth;

public class AuthException extends RuntimeException {

    public AuthException(String message) {
        super(message);
    }

    public AuthException() {
        this("Ошибка аутентификации пользователя.");
    }
}

