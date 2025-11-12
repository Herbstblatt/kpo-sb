package hse.kpo.homework2.command.impl;

import hse.kpo.homework2.command.ConsoleCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class ManageOperationsUICommand implements ConsoleCommand {

    private final ViewAllOperationsUICommand viewAllOperationsUICommand;
    private final CreateOperationUICommand createOperationUICommand;
    private final DeleteOperationUICommand deleteOperationUICommand;

    @Override
    public String getKey() {
        return "3";
    }

    @Override
    public String getDescription() {
        return "Управление операциями";
    }

    @Override
    public int getOrder() {
        return 30;
    }

    @Override
    public boolean execute(Scanner scanner) {
        while (true) {
            System.out.println("\n" + "─".repeat(70));
            System.out.println("УПРАВЛЕНИЕ ОПЕРАЦИЯМИ:");
            System.out.println("─".repeat(70));
            System.out.println("1. Просмотреть все операции");
            System.out.println("2. Создать новую операцию");
            System.out.println("3. Удалить операцию");
            System.out.println("0. Назад в главное меню");
            System.out.println("─".repeat(70));
            System.out.print("Выберите действие: ");

            String choice = scanner.nextLine().trim();

            boolean shouldContinue = switch (choice) {
                case "1" -> viewAllOperationsUICommand.execute(scanner);
                case "2" -> createOperationUICommand.execute(scanner);
                case "3" -> deleteOperationUICommand.execute(scanner);
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

