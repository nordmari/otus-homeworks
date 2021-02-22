package ru.nordmari.cell;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.nordmari.atm.NoteType;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LimitedMoneyCellTest {

    private final NoteType cellType = NoteType.TEN;
    private final LimitedMoneyCell cell = new LimitedMoneyCell(cellType);

    @ParameterizedTest
    @ValueSource(ints = {1, 99})
    void add(int inc) {
        cell.add(inc);
        assertEquals(inc * cellType.getValue(), cell.getBalance());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 23})
    void get(int dec) {
        int initNotesCount = 23;
        cell.add(initNotesCount);
        cell.get(dec);
        assertEquals((initNotesCount - dec) * cellType.getValue(), cell.getBalance());
    }
}