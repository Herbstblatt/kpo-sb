package hse.kpo.homework2.command.impl;

import hse.kpo.homework2.command.ConsoleCommand;
import hse.kpo.homework2.importexport.ExportService;
import hse.kpo.homework2.visitor.CsvExportVisitor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class ExportToCsvUICommand implements ConsoleCommand {

    private final ExportService exportService;

    @Override
    public String getKey() {
        return "export_csv";
    }

    @Override
    public String getDescription() {
        return "Экспорт данных в CSV";
    }

    @Override
    public int getOrder() {
        return -1;
    }

    @Override
    public boolean execute(Scanner scanner) {
        System.out.print("\nВведите путь к файлу для экспорта (например, export.csv): ");
        String filePath = scanner.nextLine().trim();

        if (filePath.isEmpty()) {
            filePath = "export.csv";
        }

        try {
            CsvExportVisitor visitor = new CsvExportVisitor();
            exportService.exportData(visitor, filePath);
            System.out.println("✓ Данные успешно экспортированы в " + filePath);
        } catch (Exception e) {
            System.out.println("Ошибка при экспорте: " + e.getMessage());
        }

        return true;
    }
}
