package hse.kpo.homework2.analytics;

import hse.kpo.homework2.model.Operation;
import hse.kpo.homework2.model.OperationType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Component
public class IncomeExpenseDifferenceStrategy implements AnalyticsStrategy {

    @Override
    public AnalyticsResult calculate(List<Operation> operations, LocalDateTime startDate, LocalDateTime endDate) {
        BigDecimal totalIncome = operations.stream()
                .filter(op -> op.getType() == OperationType.INCOME)
                .filter(op -> isInDateRange(op, startDate, endDate))
                .map(Operation::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalExpense = operations.stream()
                .filter(op -> op.getType() == OperationType.EXPENSE)
                .filter(op -> isInDateRange(op, startDate, endDate))
                .map(Operation::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal difference = totalIncome.subtract(totalExpense);

        return new AnalyticsResult(
                "INCOME_EXPENSE_DIFFERENCE",
                "Income vs Expense difference for the period",
                totalIncome,
                totalExpense,
                difference,
                Collections.emptyMap(),
                Collections.emptyMap()
        );
    }

    private boolean isInDateRange(Operation op, LocalDateTime start, LocalDateTime end) {
        return !op.getDate().isBefore(start) && !op.getDate().isAfter(end);
    }
}
