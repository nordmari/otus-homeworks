package ru.nordmari;

import ru.nordmari.atm.NoteType;
import ru.nordmari.cell.ATM;
import ru.nordmari.withdrawstrategy.BaseWithdrawStrategy;
import ru.nordmari.withdrawstrategy.WithdrawStrategyLoggingDecorator;

import java.util.Set;

public class Starter {

    public static void main(String[] args) {
        var withdrawStrategy = new WithdrawStrategyLoggingDecorator(new BaseWithdrawStrategy());
        var supportedNoteTypes = Set.of(NoteType.TEN, NoteType.FIVE, NoteType.ONE);
        var atm = new ATM(withdrawStrategy, supportedNoteTypes);

        // load some money to the atm
        supportedNoteTypes.forEach(
                type -> atm.replenish(50, type)
        );
        System.out.println("Money loaded. Current ATM balance: " + atm.getBalance());
        atm.withdraw(123);
        System.out.println("Money withdrew. Current ATM balance: " + atm.getBalance());
    }
}
