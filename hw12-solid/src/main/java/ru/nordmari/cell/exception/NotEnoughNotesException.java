package ru.nordmari.cell.exception;

public class NotEnoughNotesException extends RuntimeException {
    public NotEnoughNotesException(String message) {
        super(message);
    }
}
