package hse.kpo.homework2.command.impl;

import hse.kpo.homework2.command.ConsoleCommand;
import hse.kpo.homework2.facade.BankAccountFacade;
import hse.kpo.homework2.model.BankAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class ViewAllAccountsUICommand implements ConsoleCommand {

    private final BankAccountFacade accountFacade;

    @Override
    public String getKey() {
        return "view_accounts";
    }

    @Override
    public String getDescription() {
        return "Просмотреть все счета";
    }

    @Override
    public int getOrder() {
        return -1;
    }

    @Override
    public boolean execute(Scanner scanner) {
        List<BankAccount> accounts = accountFacade.getAllAccounts();

        if (accounts.isEmpty()) {
            System.out.println("\nУ вас пока нет счетов.");
            return true;
        }

        System.out.println("\n" + "═".repeat(70));
        System.out.println("ВАШИ СЧЕТА:");
        System.out.println("═".repeat(70));

        for (BankAccount account : accounts) {
            System.out.printf("ID: %-5d | Название: %-30s | Баланс: %10.2f ₽\n",
                    account.getId(), account.getName(), account.getBalance());
        }
        System.out.println("═".repeat(70));

        return true;
    }
}
