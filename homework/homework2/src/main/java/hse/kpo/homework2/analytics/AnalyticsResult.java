package hse.kpo.homework2.analytics;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@AllArgsConstructor
public class AnalyticsResult {
    private String type;
    private String description;
    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private BigDecimal difference;
    private Map<String, BigDecimal> incomeByCategory;
    private Map<String, BigDecimal> expenseByCategory;
}
