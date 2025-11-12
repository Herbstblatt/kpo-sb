package hse.kpo.homework2.facade;

import hse.kpo.homework2.analytics.AnalyticsResult;
import hse.kpo.homework2.analytics.AnalyticsStrategy;
import hse.kpo.homework2.repository.OperationRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnalyticsFacade {
    private final OperationRepository operationRepository;
    @Setter
    private AnalyticsStrategy strategy;

    public AnalyticsResult analyze(LocalDateTime startDate, LocalDateTime endDate) {
        if (strategy == null) {
            throw new IllegalStateException("Analytics strategy not set");
        }
        return strategy.calculate(operationRepository.findAll(), startDate, endDate);
    }
}
