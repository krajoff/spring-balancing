package com.example.balancing.exception.auth;

public class PermissionException extends RuntimeException {

    public PermissionException(String message) {
        super(message);
    }
}
