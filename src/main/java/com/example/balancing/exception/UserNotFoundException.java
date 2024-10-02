package com.example.balancing.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {
        super("Пользователь не найден");
    }
}
