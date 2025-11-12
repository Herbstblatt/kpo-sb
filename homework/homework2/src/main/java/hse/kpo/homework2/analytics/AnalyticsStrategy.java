package hse.kpo.homework2.analytics;

import hse.kpo.homework2.model.Operation;

import java.time.LocalDateTime;
import java.util.List;

public interface AnalyticsStrategy {
    AnalyticsResult calculate(List<Operation> operations, LocalDateTime startDate, LocalDateTime endDate);
}
