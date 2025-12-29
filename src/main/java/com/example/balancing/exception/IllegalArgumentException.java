package com.example.balancing.exception;

public class IllegalArgumentException extends RuntimeException {

    public IllegalArgumentException(EntityTypeException entityType) {
        super(String.format("[%s] Wrong argument", entityType.name()));
    }

}
