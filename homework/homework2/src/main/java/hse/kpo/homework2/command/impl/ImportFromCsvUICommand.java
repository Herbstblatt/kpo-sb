package hse.kpo.homework2.command.impl;

import hse.kpo.homework2.command.ConsoleCommand;
import hse.kpo.homework2.facade.BankAccountFacade;
import hse.kpo.homework2.facade.CategoryFacade;
import hse.kpo.homework2.facade.OperationFacade;
import hse.kpo.homework2.factory.EntityFactory;
import hse.kpo.homework2.importexport.CsvImporter;
import hse.kpo.homework2.repository.BankAccountRepository;
import hse.kpo.homework2.repository.CategoryRepository;
import hse.kpo.homework2.repository.OperationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class ImportFromCsvUICommand implements ConsoleCommand {

    private final BankAccountFacade accountFacade;
    private final CategoryFacade categoryFacade;
    private final OperationFacade operationFacade;

    @Override
    public String getKey() {
        return "import_csv";
    }

    @Override
    public String getDescription() {
        return "Импорт данных из CSV";
    }

    @Override
    public int getOrder() {
        return -1;
    }

    @Override
    public boolean execute(Scanner scanner) {
        System.out.print("\nВведите путь к CSV файлу для импорта: ");
        String filePath = scanner.nextLine().trim();

        if (filePath.isEmpty()) {
            System.out.println("Путь не может быть пустым!");
            return true;
        }

        try {
            CsvImporter importer = new CsvImporter(
                    accountFacade, categoryFacade, operationFacade
            );
            importer.importData(filePath);
            System.out.println("✓ Данные успешно импортированы из " + filePath);
        } catch (Exception e) {
            System.out.println("Ошибка при импорте: " + e.getMessage());
        }

        return true;
    }
}
