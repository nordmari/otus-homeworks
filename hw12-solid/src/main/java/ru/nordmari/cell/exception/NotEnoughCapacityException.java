package ru.nordmari.cell.exception;

public class NotEnoughCapacityException extends RuntimeException {
    public NotEnoughCapacityException(String message) {
        super(message);
    }
}
