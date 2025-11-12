package hse.kpo.homework2.command.impl;

import hse.kpo.homework2.command.ConsoleCommand;
import hse.kpo.homework2.importexport.ExportService;
import hse.kpo.homework2.visitor.JsonExportVisitor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class ExportToJsonUICommand implements ConsoleCommand {

    private final ExportService exportService;

    @Override
    public String getKey() {
        return "export_json";
    }

    @Override
    public String getDescription() {
        return "Экспорт данных в JSON";
    }

    @Override
    public int getOrder() {
        return -1;
    }

    @Override
    public boolean execute(Scanner scanner) {
        System.out.print("\nВведите путь к файлу для экспорта (например, export.json): ");
        String filePath = scanner.nextLine().trim();

        if (filePath.isEmpty()) {
            filePath = "export.json";
        }

        try {
            // Используем паттерн VISITOR
            JsonExportVisitor visitor = new JsonExportVisitor();
            exportService.exportData(visitor, filePath);
            System.out.println("✓ Данные успешно экспортированы в " + filePath);
        } catch (Exception e) {
            System.out.println("Ошибка при экспорте: " + e.getMessage());
        }

        return true;
    }
}
