package com.example.balancing.exception.user;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {
        super("Пользователь не найден");
    }
}
