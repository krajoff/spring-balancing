package com.example.balancing.exception;

public class ElementAlreadyExistsException extends RuntimeException {

    public ElementAlreadyExistsException(EntityTypeException entityType) {
        super(String.format("[%s] Element already existed", entityType.name()));
    }

}
