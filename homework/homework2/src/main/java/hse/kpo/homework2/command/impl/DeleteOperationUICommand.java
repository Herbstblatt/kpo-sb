package hse.kpo.homework2.command.impl;

import hse.kpo.homework2.command.ConsoleCommand;
import hse.kpo.homework2.facade.OperationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class DeleteOperationUICommand implements ConsoleCommand {

    private final OperationFacade operationFacade;
    private final ViewAllOperationsUICommand viewAllOperationsUICommand;

    @Override
    public String getKey() {
        return "delete_operation";
    }

    @Override
    public String getDescription() {
        return "Удалить операцию";
    }

    @Override
    public int getOrder() {
        return -1;
    }

    @Override
    public boolean execute(Scanner scanner) {
        viewAllOperationsUICommand.execute(scanner);

        if (operationFacade.getAllOperations().isEmpty()) {
            return true;
        }

        System.out.print("\nВведите ID операции для удаления: ");
        Long id;
        try {
            id = Long.parseLong(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат ID!");
            return true;
        }

        operationFacade.deleteOperation(id);
        System.out.println("✓ Операция успешно удалена!");
        return true;
    }
}
