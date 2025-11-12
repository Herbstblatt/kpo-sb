package hse.kpo.homework2.command.impl;

import hse.kpo.homework2.command.ConsoleCommand;
import hse.kpo.homework2.facade.BankAccountFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class CreateAccountUICommand implements ConsoleCommand {

    private final BankAccountFacade accountFacade;

    @Override
    public String getKey() {
        return "create_account";
    }

    @Override
    public String getDescription() {
        return "Создать новый счет";
    }

    @Override
    public int getOrder() {
        return -1;
    }

    @Override
    public boolean execute(Scanner scanner) {
        System.out.print("\nВведите название счета: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Название не может быть пустым!");
            return true;
        }

        System.out.print("Введите начальный баланс: ");
        BigDecimal balance;
        try {
            balance = new BigDecimal(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат суммы!");
            return true;
        }

        accountFacade.createAccount(name, balance);

        System.out.println("✓ Счет успешно создан!");
        return true;
    }
}
