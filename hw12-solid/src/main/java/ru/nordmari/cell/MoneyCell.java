package ru.nordmari.cell;

import ru.nordmari.atm.NoteType;
import ru.nordmari.cell.exception.NotEnoughCapacityException;
import ru.nordmari.cell.exception.NotEnoughNotesException;

/**
 * Базовый интерфейс для денежной ячейки
 */
public interface MoneyCell {

    int getBalance();

    NoteType getType();

    void get(int dec) throws NotEnoughNotesException;

    void add(int inc) throws NotEnoughCapacityException;

}
