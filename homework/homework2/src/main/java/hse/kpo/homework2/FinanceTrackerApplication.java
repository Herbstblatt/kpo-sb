package hse.kpo.homework2;

import hse.kpo.homework2.command.CommandRegistry;
import hse.kpo.homework2.command.ConsoleCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
@RequiredArgsConstructor
public class FinanceTrackerApplication implements CommandLineRunner {

    private final CommandRegistry commandRegistry;

    public static void main(String[] args) {
        SpringApplication.run(FinanceTrackerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        printWelcome();
        var scanner = new Scanner(System.in);

        boolean running = true;
        while (running) {
            System.out.println(commandRegistry.formatMenu());
            System.out.print("Выберите пункт меню: ");

            String input = scanner.nextLine().trim();

            var commandOpt = commandRegistry.getCommand(input);
            if (commandOpt.isPresent()) {
                ConsoleCommand command = commandOpt.get();
                try {
                    running = command.execute(scanner);
                } catch (Exception e) {
                    System.out.println("⚠ Ошибка выполнения команды: " + e.getMessage());
                }
            } else {
                System.out.println("⚠ Неизвестная команда.");
            }

            if (running) {
                System.out.println("\nНажмите Enter для продолжения...");
                scanner.nextLine();
            }
        }
    }

    private void printWelcome() {
        System.out.println("═".repeat(70));
        System.out.println("  ВШЭ-БАНК ");
        System.out.println("═".repeat(70));
        System.out.println();
    }
}