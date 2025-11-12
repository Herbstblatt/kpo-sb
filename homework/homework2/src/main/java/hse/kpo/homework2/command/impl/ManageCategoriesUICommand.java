package hse.kpo.homework2.command.impl;

import hse.kpo.homework2.command.ConsoleCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class ManageCategoriesUICommand implements ConsoleCommand {

    private final ViewAllCategoriesUICommand viewAllCategoriesUICommand;
    private final CreateCategoryUICommand createCategoryUICommand;
    private final UpdateCategoryUICommand updateCategoryUICommand;
    private final DeleteCategoryUICommand deleteCategoryUICommand;

    @Override
    public String getKey() {
        return "2";
    }

    @Override
    public String getDescription() {
        return "Управление категориями";
    }

    @Override
    public int getOrder() {
        return 20;
    }

    @Override
    public boolean execute(Scanner scanner) {
        while (true) {
            System.out.println("\n" + "─".repeat(70));
            System.out.println("УПРАВЛЕНИЕ КАТЕГОРИЯМИ:");
            System.out.println("─".repeat(70));
            System.out.println("1. Просмотреть все категории");
            System.out.println("2. Создать новую категорию");
            System.out.println("3. Изменить название категории");
            System.out.println("4. Удалить категорию");
            System.out.println("0. Назад в главное меню");
            System.out.println("─".repeat(70));
            System.out.print("Выберите действие: ");

            String choice = scanner.nextLine().trim();

            boolean shouldContinue = switch (choice) {
                case "1" -> viewAllCategoriesUICommand.execute(scanner);
                case "2" -> createCategoryUICommand.execute(scanner);
                case "3" -> updateCategoryUICommand.execute(scanner);
                case "4" -> deleteCategoryUICommand.execute(scanner);
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