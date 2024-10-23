package com.example.balancing.exception.token;

public class RefreshTokenExpiredException extends RuntimeException{
    public RefreshTokenExpiredException(String message) {super(message);}

    public RefreshTokenExpiredException() {
        this("Время жизни рефреш-токен истекло");
    }
}
