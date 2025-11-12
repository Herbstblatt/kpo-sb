package hse.kpo.homework2.command.impl;

import hse.kpo.homework2.command.ConsoleCommand;
import hse.kpo.homework2.facade.BankAccountFacade;
import hse.kpo.homework2.facade.CategoryFacade;
import hse.kpo.homework2.facade.OperationFacade;
import hse.kpo.homework2.model.OperationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class CreateOperationUICommand implements ConsoleCommand {

    private final OperationFacade operationFacade;
    private final BankAccountFacade accountFacade;
    private final CategoryFacade categoryFacade;
    private final ViewAllAccountsUICommand viewAllAccountsUICommand;
    private final ViewAllCategoriesUICommand viewAllCategoriesUICommand;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @Override
    public String getKey() {
        return "create_operation";
    }

    @Override
    public String getDescription() {
        return "Создать новую операцию";
    }

    @Override
    public int getOrder() {
        return -1;
    }

    @Override
    public boolean execute(Scanner scanner) {
        // Проверяем наличие счетов
        if (accountFacade.getAllAccounts().isEmpty()) {
            System.out.println("\nСначала создайте хотя бы один счет!");
            return true;
        }

        // Проверяем наличие категорий
        if (categoryFacade.getAllCategories().isEmpty()) {
            System.out.println("\nСначала создайте хотя бы одну категорию!");
            return true;
        }

        System.out.println("\nТип операции:");
        System.out.println("1. Доход");
        System.out.println("2. Расход");
        System.out.print("Выберите тип: ");

        String typeChoice = scanner.nextLine().trim();
        OperationType type;

        switch (typeChoice) {
            case "1" -> type = OperationType.INCOME;
            case "2" -> type = OperationType.EXPENSE;
            default -> {
                System.out.println("Неверный выбор!");
                return true;
            }
        }

        // Выбор счета
        viewAllAccountsUICommand.execute(scanner);
        System.out.print("\nВведите ID счета: ");
        Long accountId;
        try {
            accountId = Long.parseLong(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат ID!");
            return true;
        }

        // Выбор категории
        viewAllCategoriesUICommand.execute(scanner);
        System.out.print("\nВведите ID категории: ");
        Long categoryId;
        try {
            categoryId = Long.parseLong(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат ID!");
            return true;
        }

        // Сумма
        System.out.print("Введите сумму: ");
        BigDecimal amount;
        try {
            amount = new BigDecimal(scanner.nextLine().trim());
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                System.out.println("Сумма должна быть положительной!");
                return true;
            }
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат суммы!");
            return true;
        }

        // Дата
        System.out.print("Введите дату и время (формат: дд.мм.гггг чч:мм) или Enter для текущего времени: ");
        String dateStr = scanner.nextLine().trim();
        LocalDateTime date;

        if (dateStr.isEmpty()) {
            date = LocalDateTime.now();
        } else {
            try {
                date = LocalDateTime.parse(dateStr, dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Неверный формат даты!");
                return true;
            }
        }

        // Описание
        System.out.print("Введите описание (необязательно): ");
        String description = scanner.nextLine().trim();

        operationFacade.createOperation(type, accountId, amount, date, description, categoryId);

        System.out.println("✓ Операция успешно создана!");
        return true;
    }
}
