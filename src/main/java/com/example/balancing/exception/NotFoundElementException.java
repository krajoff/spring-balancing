package com.example.balancing.exception;

public class NotFoundElementException extends RuntimeException {

    public NotFoundElementException(EntityTypeException entityType) {
        super(String.format("[%s] No element found", entityType.name()));
    }

}
