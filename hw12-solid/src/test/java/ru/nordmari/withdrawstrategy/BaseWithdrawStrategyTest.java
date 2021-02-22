package ru.nordmari.withdrawstrategy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.nordmari.atm.NoteType;
import ru.nordmari.cell.MoneyCell;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.nordmari.atm.NoteType.FIFTY;
import static ru.nordmari.atm.NoteType.FIVE;
import static ru.nordmari.atm.NoteType.ONE;
import static ru.nordmari.atm.NoteType.TEN;

class BaseWithdrawStrategyTest {

    private final BaseWithdrawStrategy strategy = new BaseWithdrawStrategy();

    @ParameterizedTest
    @MethodSource("combineSource")
    void combine(int amount, Collection<? extends MoneyCell> cells, Map<NoteType, Integer> expectedResult) {
        assertThat(strategy.combine(amount, cells)).containsExactlyInAnyOrderEntriesOf(expectedResult);
    }

    private static Stream<Arguments> combineSource() {
        return Stream.of(
                Arguments.of(1, List.of(new TestMoneyCell(ONE, 1)), Map.of(ONE, 1)),
                Arguments.of(50, List.of(new TestMoneyCell(FIFTY, 1)), Map.of(FIFTY, 1)),
                Arguments.of(51,
                        List.of(new TestMoneyCell(ONE, 1),
                                new TestMoneyCell(FIFTY, 1)),
                        Map.of(ONE, 1,
                                FIFTY, 1)),

                Arguments.of(61,
                        List.of(new TestMoneyCell(ONE, 1),
                                new TestMoneyCell(TEN, 1),
                                new TestMoneyCell(FIFTY, 1)),
                        Map.of(ONE, 1,
                                TEN, 1,
                                FIFTY, 1)),

                Arguments.of(69,
                        List.of(new TestMoneyCell(ONE, 100),
                                new TestMoneyCell(TEN, 100),
                                new TestMoneyCell(FIFTY, 100)),
                        Map.of(ONE, 9,
                                TEN, 1,
                                FIFTY, 1)),

                Arguments.of(1010,
                        List.of(new TestMoneyCell(ONE, 100),
                                new TestMoneyCell(TEN, 100),
                                new TestMoneyCell(FIFTY, 100)),
                        Map.of(TEN, 1,
                                FIFTY, 20)),

                Arguments.of(100,
                        List.of(new TestMoneyCell(ONE, 0),
                                new TestMoneyCell(TEN, 100),
                                new TestMoneyCell(FIFTY, 0)),
                        Map.of(TEN, 10)),

                Arguments.of(40,
                        List.of(new TestMoneyCell(ONE, 10),
                                new TestMoneyCell(FIVE, 2),
                                new TestMoneyCell(TEN, 3),
                                new TestMoneyCell(FIFTY, 1)),
                        Map.of(
                                TEN, 3,
                                FIVE, 2
                                ))
        );
    }

    private static class TestMoneyCell implements MoneyCell {
        private final NoteType type;
        private final int balance;

        public TestMoneyCell(NoteType type, int notes) {
            this.type = type;
            this.balance = notes * type.getValue();
        }

        @Override
        public int getBalance() {
            return balance;
        }

        @Override
        public NoteType getType() {
            return type;
        }

        @Override
        public void add(int inc) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void get(int dec) {
            throw new UnsupportedOperationException();
        }
    }

}