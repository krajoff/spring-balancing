package com.example.balancing.exception.user;

public class UserAlreadyExistedException extends RuntimeException {

    public UserAlreadyExistedException(String message) {
        super(message);
    }

    public UserAlreadyExistedException() {
        this("Такой пользователь уже существует");
    }
}
