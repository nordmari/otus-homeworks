package ru.nordmari.withdrawstrategy;

import ru.nordmari.atm.NoteType;
import ru.nordmari.cell.MoneyCell;
import ru.nordmari.withdrawstrategy.exception.UnableToCombineException;

import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class BaseWithdrawStrategy implements WithdrawStrategy {

    @Override
    public Map<NoteType, Integer> combine(int amount, Collection<? extends MoneyCell> cells) {
        List<MoneyCell> sortedCells = cells.stream()
                .filter(c -> c.getType().getValue() <= amount)
                .sorted(((c1, c2) -> Integer.compare(c2.getType().getValue(), c1.getType().getValue()))) // desc order
                .collect(toList());

        Map<NoteType, Integer> result = new EnumMap<>(NoteType.class);
        int leftAmount = amount;
        for (MoneyCell cell : sortedCells) {
            if (cell.getType().getValue() > leftAmount || cell.getBalance() == 0) {
                continue;
            }
            int neededBalanceFromCell = Math.min(leftAmount, cell.getBalance());
            int cellNoteCount = neededBalanceFromCell / cell.getType().getValue();
            result.put(cell.getType(), cellNoteCount);
            leftAmount -= cellNoteCount * cell.getType().getValue();
        }

        if (leftAmount > 0) {
            throw new UnableToCombineException("Impossible to withdraw amount " + amount);
        }

        return result;
    }
}
