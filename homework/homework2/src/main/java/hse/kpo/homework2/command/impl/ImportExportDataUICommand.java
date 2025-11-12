package hse.kpo.homework2.command.impl;

import hse.kpo.homework2.command.ConsoleCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class ImportExportDataUICommand implements ConsoleCommand {

    private final ExportToCsvUICommand exportToCsvUICommand;
    private final ExportToJsonUICommand exportToJsonUICommand;
    private final ImportFromCsvUICommand importFromCsvUICommand;
    private final ImportFromJsonUICommand importFromJsonUICommand;

    @Override
    public String getKey() {
        return "5";
    }

    @Override
    public String getDescription() {
        return "Импорт/Экспорт данных";
    }

    @Override
    public int getOrder() {
        return 50;
    }

    @Override
    public boolean execute(Scanner scanner) {
        while (true) {
            System.out.println("\n" + "─".repeat(70));
            System.out.println("ИМПОРТ/ЭКСПОРТ ДАННЫХ:");
            System.out.println("─".repeat(70));
            System.out.println("1. Экспорт данных в CSV");
            System.out.println("2. Экспорт данных в JSON");
            System.out.println("3. Импорт данных из CSV");
            System.out.println("4. Импорт данных из JSON");
            System.out.println("0. Назад в главное меню");
            System.out.println("─".repeat(70));
            System.out.print("Выберите действие: ");

            String choice = scanner.nextLine().trim();

            boolean shouldContinue = switch (choice) {
                case "1" -> exportToCsvUICommand.execute(scanner);
                case "2" -> exportToJsonUICommand.execute(scanner);
                case "3" -> importFromCsvUICommand.execute(scanner);
                case "4" -> importFromJsonUICommand.execute(scanner);
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

