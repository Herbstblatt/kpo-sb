package hse.kpo.homework2.command.impl;

import hse.kpo.homework2.command.ConsoleCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class ManageBankAccountsUICommand implements ConsoleCommand {

    private final ViewAllAccountsUICommand viewAllAccountsUICommand;
    private final CreateAccountUICommand createAccountUICommand;
    private final UpdateAccountUICommand updateAccountUICommand;
    private final DeleteAccountUICommand deleteAccountUICommand;

    @Override
    public String getKey() {
        return "1";
    }

    @Override
    public String getDescription() {
        return "Управление счетами";
    }

    @Override
    public int getOrder() {
        return 10;
    }

    @Override
    public boolean execute(Scanner scanner) {
        while (true) {
            System.out.println("\n" + "─".repeat(70));
            System.out.println("УПРАВЛЕНИЕ СЧЕТАМИ:");
            System.out.println("─".repeat(70));
            System.out.println("1. Просмотреть все счета");
            System.out.println("2. Создать новый счет");
            System.out.println("3. Изменить название счета");
            System.out.println("4. Удалить счет");
            System.out.println("0. Назад в главное меню");
            System.out.println("─".repeat(70));
            System.out.print("Выберите действие: ");

            String choice = scanner.nextLine().trim();

            boolean shouldContinue = switch (choice) {
                case "1" -> viewAllAccountsUICommand.execute(scanner);
                case "2" -> createAccountUICommand.execute(scanner);
                case "3" -> updateAccountUICommand.execute(scanner);
                case "4" -> deleteAccountUICommand.execute(scanner);
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
