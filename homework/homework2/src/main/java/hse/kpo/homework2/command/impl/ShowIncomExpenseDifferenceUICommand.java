package hse.kpo.homework2.command.impl;

import hse.kpo.homework2.analytics.AnalyticsResult;
import hse.kpo.homework2.analytics.IncomeExpenseDifferenceStrategy;
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
public class ShowIncomExpenseDifferenceUICommand implements ConsoleCommand {

    private final AnalyticsFacade analyticsFacade;
    private final IncomeExpenseDifferenceStrategy differenceStrategy;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @Override
    public String getKey() {
        return "analytics_difference";
    }

    @Override
    public String getDescription() {
        return "Разница доходов и расходов за период";
    }

    @Override
    public int getOrder() {
        return -1;
    }

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

        analyticsFacade.setStrategy(differenceStrategy);
        AnalyticsResult result = analyticsFacade.analyze(startDate, endDate);

        var totalIncome = result.getTotalIncome();
        var totalExpense = result.getTotalExpense();

        System.out.println("\n" + "═".repeat(70));
        System.out.println("АНАЛИТИКА ЗА ПЕРИОД: " + startDate.format(dateFormatter) + " - " + endDate.format(dateFormatter));
        System.out.println("═".repeat(70));
        System.out.printf("Общий доход:    %15.2f ₽\n", totalIncome);
        System.out.printf("Общий расход:   %15.2f ₽\n", totalExpense);
        System.out.printf("Разница:        %15.2f ₽\n", totalIncome.subtract(totalExpense));
        System.out.println("═".repeat(70));

        return true;
    }
}

