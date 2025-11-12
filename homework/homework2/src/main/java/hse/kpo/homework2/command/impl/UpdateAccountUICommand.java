package hse.kpo.homework2.command.impl;

import hse.kpo.homework2.command.ConsoleCommand;
import hse.kpo.homework2.facade.BankAccountFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class UpdateAccountUICommand implements ConsoleCommand {

    private final BankAccountFacade accountFacade;
    private final ViewAllAccountsUICommand viewAllAccountsUICommand;

    @Override
    public String getKey() {
        return "update_account";
    }

    @Override
    public String getDescription() {
        return "Изменить название счета";
    }

    @Override
    public int getOrder() {
        return -1;
    }

    @Override
    public boolean execute(Scanner scanner) {
        viewAllAccountsUICommand.execute(scanner);

        if (accountFacade.getAllAccounts().isEmpty()) {
            return true;
        }

        System.out.print("\nВведите ID счета для изменения: ");
        Long id;
        try {
            id = Long.parseLong(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат ID!");
            return true;
        }

        System.out.print("Введите новое название: ");
        String newName = scanner.nextLine().trim();

        if (newName.isEmpty()) {
            System.out.println("Название не может быть пустым!");
            return true;
        }

        accountFacade.updateAccount(id, newName, null);

        System.out.println("✓ Счет успешно обновлен!");
        return true;
    }
}
