package ru.nordmari.atm;

/**
 * Тип банкноты
 * value - номинал
 */
public enum NoteType {
    ONE(1),
    FIVE(5),
    TEN(10),
    FIFTY(50);

    private final int value;

    NoteType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
