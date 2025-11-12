package hse.kpo.homework2.command.impl;

import hse.kpo.homework2.command.ConsoleCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class ShowAnalyticsUICommand implements ConsoleCommand {

    private final ShowIncomExpenseDifferenceUICommand showIncomExpenseDifferenceUICommand;
    private final ShowCategoryGroupingUICommand showCategoryGroupingUICommand;

    @Override
    public String getKey() {
        return "4";
    }

    @Override
    public String getDescription() {
        return "Аналитика";
    }

    @Override
    public int getOrder() {
        return 40;
    }

    @Override
    public boolean execute(Scanner scanner) {
        while (true) {
            System.out.println("\n" + "─".repeat(70));
            System.out.println("АНАЛИТИКА:");
            System.out.println("─".repeat(70));
            System.out.println("1. Разница доходов и расходов за период");
            System.out.println("2. Группировка операций по категориям");
            System.out.println("0. Назад в главное меню");
            System.out.println("─".repeat(70));
            System.out.print("Выберите действие: ");

            String choice = scanner.nextLine().trim();

            boolean shouldContinue = switch (choice) {
                case "1" -> showIncomExpenseDifferenceUICommand.execute(scanner);
                case "2" -> showCategoryGroupingUICommand.execute(scanner);
                case "0" -> false;
                default -> {
                    System.out.println("Неверный выбор.");
                    yield true;
                }
            };

            if (!shouldContinue) {
                return true;
            }
        }
    }
}
