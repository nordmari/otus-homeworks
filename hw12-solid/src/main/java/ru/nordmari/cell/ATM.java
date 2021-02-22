package ru.nordmari.cell;

import ru.nordmari.atm.NoteType;
import ru.nordmari.atm.exception.NotEnoughBalanceException;
import ru.nordmari.atm.exception.UnsupportedNoteException;
import ru.nordmari.withdrawstrategy.WithdrawStrategy;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static java.util.stream.Collectors.toUnmodifiableMap;

public class ATM {

    private final WithdrawStrategy withdrawStrategy;
    private final Map<NoteType, MoneyCell> cellsByType;

    public ATM(WithdrawStrategy withdrawStrategy, Set<NoteType> noteTypes) {
        this.withdrawStrategy = withdrawStrategy;
        this.cellsByType = noteTypes.stream()
                .collect(toUnmodifiableMap(Function.identity(), LimitedMoneyCell::new));
    }

    public void withdraw(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Negative amount " + amount);
        }
        if (getBalance() < amount) {
            throw new NotEnoughBalanceException("Not enough money");
        }
        withdrawStrategy.combine(amount, getCells()).forEach(
                ((moneyType, requiredNotesCount) -> getCellOfType(moneyType).get(requiredNotesCount))
        );
    }

    public void replenish(int amount, NoteType type) {
        getCellOfType(type).add(amount);
    }

    private MoneyCell getCellOfType(NoteType type) {
        var cell = cellsByType.get(type);
        if (cell == null) {
            throw new UnsupportedNoteException("Unsupported note type: " + type);
        }
        return cell;
    }

    public int getBalance() {
        return getCells().stream()
                .mapToInt(MoneyCell::getBalance)
                .sum();
    }

    private Collection<MoneyCell> getCells() {
        return cellsByType.values();
    }

}
