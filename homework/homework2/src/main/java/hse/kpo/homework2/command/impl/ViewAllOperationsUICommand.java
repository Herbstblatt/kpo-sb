package hse.kpo.homework2.command.impl;

import hse.kpo.homework2.command.ConsoleCommand;
import hse.kpo.homework2.facade.BankAccountFacade;
import hse.kpo.homework2.facade.CategoryFacade;
import hse.kpo.homework2.facade.OperationFacade;
import hse.kpo.homework2.model.Operation;
import hse.kpo.homework2.model.OperationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class ViewAllOperationsUICommand implements ConsoleCommand {

    private final OperationFacade operationFacade;
    private final CategoryFacade categoryFacade;
    private final BankAccountFacade accountFacade;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @Override
    public String getKey() {
        return "view_operations";
    }

    @Override
    public String getDescription() {
        return "Просмотреть все операции";
    }

    @Override
    public int getOrder() {
        return -1;
    }

    @Override
    public boolean execute(Scanner scanner) {
        List<Operation> operations = operationFacade.getAllOperations();

        if (operations.isEmpty()) {
            System.out.println("\nУ вас пока нет операций.");
            return true;
        }

        System.out.println("\n" + "═".repeat(70));
        System.out.println("ВАШИ ОПЕРАЦИИ:");
        System.out.println("═".repeat(70));

        for (Operation op : operations) {
            String typeSymbol = op.getType() == OperationType.INCOME ? "+" : "-";
            String categoryName = categoryFacade.getCategory(op.getCategoryId())
                    .getName();
            String accountName = accountFacade.getAccount(op.getBankAccountId())
                    .getName();

            System.out.printf("ID: %-5d | %s | %s %10.2f ₽ | Счет: %-20s | Категория: %-15s\n",
                    op.getId(),
                    op.getDate().format(dateFormatter),
                    typeSymbol,
                    op.getAmount(),
                    accountName,
                    categoryName);

            if (op.getDescription() != null && !op.getDescription().isEmpty()) {
                System.out.println("           Описание: " + op.getDescription());
            }
        }
        System.out.println("═".repeat(70));

        return true;
    }
}
