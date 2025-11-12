package hse.kpo.homework2.analytics;

import hse.kpo.homework2.model.Category;
import hse.kpo.homework2.model.Operation;
import hse.kpo.homework2.model.OperationType;
import hse.kpo.homework2.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CategoryGroupingStrategy implements AnalyticsStrategy {
    private final CategoryRepository categoryRepository;

    @Override
    public AnalyticsResult calculate(List<Operation> operations, LocalDateTime startDate, LocalDateTime endDate) {
        Map<String, BigDecimal> incomeByCategory = new HashMap<>();
        Map<String, BigDecimal> expenseByCategory = new HashMap<>();
        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalExpense = BigDecimal.ZERO;

        for (Operation op : operations) {
            if (isInDateRange(op, startDate, endDate)) {
                Category category = categoryRepository.findById(op.getCategoryId()).orElse(null);
                String categoryName = category != null ? category.getName() : "Unknown";

                if (op.getType() == OperationType.INCOME) {
                    totalIncome = totalIncome.add(op.getAmount());
                    incomeByCategory.merge(categoryName, op.getAmount(), BigDecimal::add);
                } else {
                    totalExpense = totalExpense.add(op.getAmount());
                    expenseByCategory.merge(categoryName, op.getAmount(), BigDecimal::add);
                }
            }
        }

        return new AnalyticsResult(
                "CATEGORY_GROUPING",
                "Operations grouped by categories",
                totalIncome,
                totalExpense,
                totalIncome.subtract(totalExpense),
                incomeByCategory,
                expenseByCategory
        );
    }

    private boolean isInDateRange(Operation op, LocalDateTime start, LocalDateTime end) {
        return !op.getDate().isBefore(start) && !op.getDate().isAfter(end);
    }
}
