package com.example.balancing.exception.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WrongRequestException extends RuntimeException {
    public WrongRequestException(String message) {
        super(message);
    }

    public WrongRequestException() {
        this("Ошибка: некорректные данные, проверьте поля запроса.");
    }

}
