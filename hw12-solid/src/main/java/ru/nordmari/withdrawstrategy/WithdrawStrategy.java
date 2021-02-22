package ru.nordmari.withdrawstrategy;

import ru.nordmari.atm.NoteType;
import ru.nordmari.cell.MoneyCell;

import java.util.Collection;
import java.util.Map;

/**
 * Стратегия выдачи запрошенной суммы
 * amount - запрошенная сумма
 * cells - ячейки, из которых должен осуществляться подбор
 */
public interface WithdrawStrategy {
    Map<NoteType, Integer> combine(int amount, Collection<? extends MoneyCell> cells);
}
