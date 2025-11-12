package hse.kpo.homework2.command.impl;

import hse.kpo.homework2.analytics.AnalyticsResult;
import hse.kpo.homework2.analytics.CategoryGroupingStrategy;
import hse.kpo.homework2.command.ConsoleCommand;
import hse.kpo.homework2.facade.AnalyticsFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class ShowCategoryGroupingUICommand implements ConsoleCommand {

    private final AnalyticsFacade analyticsFacade;
    private final CategoryGroupingStrategy groupingStrategy;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @Override
    public String getKey() {
        return "analytics_grouping";
    }

    @Override
    public String getDescription() {
        return "Группировка операций по категориям";
    }

    @Override
    public int getOrder() {
        return -1;
    }

    @Override
    public boolean execute(Scanner scanner) {
        System.out.print("\nВведите начальную дату (формат: дд.мм.гггг чч:мм): ");
        String startStr = scanner.nextLine().trim();

        System.out.print("Введите конечную дату (формат: дд.мм.гггг чч:мм): ");
        String endStr = scanner.nextLine().trim();

        LocalDateTime startDate, endDate;
        try {
            startDate = LocalDateTime.parse(startStr, dateFormatter);
            endDate = LocalDateTime.parse(endStr, dateFormatter);
        } catch (DateTimeParseException e) {
            System.out.println("Неверный формат даты!");
            return true;
        }

        analyticsFacade.setStrategy(groupingStrategy);
        AnalyticsResult result = analyticsFacade.analyze(startDate, endDate);

        System.out.println("\n" + "═".repeat(70));
        System.out.println("ГРУППИРОВКА ПО КАТЕГОРИЯМ ЗА ПЕРИОД:");
        System.out.println("═".repeat(70));

        System.out.println("\n▼ ДОХОДЫ:");
        if (result.getIncomeByCategory().isEmpty()) {
            System.out.println("  Нет доходов за этот период");
        } else {
            result.getIncomeByCategory().forEach((category, amount) ->
                    System.out.printf("  %-30s: %12.2f ₽\n", category, amount));
        }

        System.out.println("\n▼ РАСХОДЫ:");
        if (result.getExpenseByCategory().isEmpty()) {
            System.out.println("  Нет расходов за этот период");
        } else {
            result.getExpenseByCategory().forEach((category, amount) ->
                    System.out.printf("  %-30s: %12.2f ₽\n", category, amount));
        }

        System.out.println("═".repeat(70));

        return true;
    }
}
