package hse.kpo.homework2.command.impl;

import hse.kpo.homework2.command.ConsoleCommand;
import org.springframework.stereotype.Component;
import java.util.Scanner;

@Component
public class ExitUICommand implements ConsoleCommand {
    @Override
    public String getKey() {
        return "0";
    }

    @Override
    public String getDescription() {
        return "Выход";
    }

    @Override
    public int getOrder() {
        return 1000;
    }

    @Override
    public boolean execute(Scanner scanner) {
        System.out.println("\n👋 Работа завершена.");
        return false;
    }
}
