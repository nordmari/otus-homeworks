package ru.nordmari.cell;

import ru.nordmari.atm.NoteType;
import ru.nordmari.cell.exception.NotEnoughCapacityException;
import ru.nordmari.cell.exception.NotEnoughNotesException;

/**
 * Ячейка с ограниченной ёмкостью
 */
public class LimitedMoneyCell implements MoneyCell {
    private static final int MAX_CAPACITY = 500;
    private final NoteType type;
    private int notesCount;

    public LimitedMoneyCell(NoteType type) {
        this.type = type;
    }

    @Override
    public int getBalance() {
        return notesCount * type.getValue();
    }

    @Override
    public NoteType getType() {
        return type;
    }

    @Override
    public void add(int inc) {
        if (notesCount + inc > MAX_CAPACITY) {
            throw new NotEnoughCapacityException("Not enough capacity to replenish " + inc);
        }
        notesCount += inc;
    }

    @Override
    public void get(int dec) {
        if (notesCount < dec) {
            throw new NotEnoughNotesException("Not enough banknotes to withdraw " + dec);
        }
        notesCount -= dec;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LimitedMoneyCell that = (LimitedMoneyCell) o;

        return type == that.type;
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }

    @Override
    public String toString() {
        return "LimitedMoneyCell{" +
                "type=" + type +
                ", notesCount=" + notesCount +
                '}';
    }
}
