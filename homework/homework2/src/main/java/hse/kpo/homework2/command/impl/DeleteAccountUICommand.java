package hse.kpo.homework2.command.impl;

import hse.kpo.homework2.command.ConsoleCommand;
import hse.kpo.homework2.facade.BankAccountFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class DeleteAccountUICommand implements ConsoleCommand {

    private final BankAccountFacade accountFacade;
    private final ViewAllAccountsUICommand viewAllAccountsUICommand;

    @Override
    public String getKey() {
        return "delete_account";
    }

    @Override
    public String getDescription() {
        return "Удалить счет";
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

        System.out.print("\nВведите ID счета для удаления: ");
        Long id;
        try {
            id = Long.parseLong(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат ID!");
            return true;
        }

        System.out.print("Вы уверены? Это удалит все операции по счету! (да/нет): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (!confirm.equals("да") && !confirm.equals("yes")) {
            System.out.println("Удаление отменено.");
            return true;
        }

        accountFacade.deleteAccount(id);

        System.out.println("✓ Счет успешно удален!");
        return true;
    }
}
