package com.example.balancing.exception;

public class PlaneNotCorrectException extends RuntimeException{
    public PlaneNotCorrectException() {
        super("Задана ссылка на пуск с плоскость, " +
                "которая отличается то выбранной на текущем пуске.");
    }
}
