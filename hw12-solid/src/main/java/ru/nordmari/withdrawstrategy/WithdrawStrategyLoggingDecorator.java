package ru.nordmari.withdrawstrategy;

import ru.nordmari.atm.NoteType;
import ru.nordmari.cell.MoneyCell;

import java.util.Collection;
import java.util.Map;

public class WithdrawStrategyLoggingDecorator implements WithdrawStrategy {

    private final WithdrawStrategy strategy;

    public WithdrawStrategyLoggingDecorator(WithdrawStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public Map<NoteType, Integer> combine(int amount, Collection<? extends MoneyCell> cells) {
        System.out.println("~~ Requested amount: " + amount);
        System.out.println("~~ Provided cells: " + cells);
        var result = strategy.combine(amount, cells);
        System.out.println("~~ Combination result: " + result);
        return result;
    }
}
