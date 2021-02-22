package ru.nordmari.atm;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.nordmari.cell.ATM;
import ru.nordmari.withdrawstrategy.BaseWithdrawStrategy;
import ru.nordmari.withdrawstrategy.WithdrawStrategy;

import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.nordmari.atm.NoteType.FIFTY;
import static ru.nordmari.atm.NoteType.FIVE;
import static ru.nordmari.atm.NoteType.ONE;
import static ru.nordmari.atm.NoteType.TEN;

class ATMTest {

    private final WithdrawStrategy atmStrategy = new BaseWithdrawStrategy();
    private final ATM atm = new ATM(atmStrategy, Set.of(ONE, FIVE, TEN, FIFTY));

    @ParameterizedTest
    @MethodSource("replenishSource")
    void replenish(Map<NoteType, Integer> replenishmentNotes, int expectedBalance) {
        replenishmentNotes.forEach((type, amount) -> atm.replenish(amount, type));
        assertEquals(expectedBalance, atm.getBalance());
    }

    private static Stream<Arguments> replenishSource() {
        return Stream.of(
                Arguments.of(Map.of(
                        ONE, 1,
                        FIVE, 1,
                        TEN, 1,
                        FIFTY, 1
                ), 66),
                Arguments.of(Map.of(
                        ONE, 10,
                        FIVE, 10,
                        TEN, 10,
                        FIFTY, 10
                ), 660),
                Arguments.of(Map.of(
                        FIVE, 2
                ), 10)
        );
    }

    @ParameterizedTest
    @MethodSource("withdrawSource")
    void withdraw(Map<NoteType, Integer> initNotes) {
        initNotes.forEach((type, amount) -> atm.replenish(amount, type));

        atm.withdraw(40);
        assertEquals(60, atm.getBalance());
    }

    // sum balance is always 100
    private static Stream<Arguments> withdrawSource() {
        return Stream.of(
                Arguments.of(Map.of(
                        ONE, 100
                )),
                Arguments.of(Map.of(
                        ONE, 10,
                        FIVE, 2,
                        TEN, 3,
                        FIFTY, 1
                )),
                Arguments.of(Map.of(
                        FIVE, 20
                ))
        );
    }

}
