package hse.kpo.homework2.command;

import java.util.Scanner;

public interface ConsoleCommand {
    String getKey();
    String getDescription();
    boolean execute(Scanner scanner);
    default int getOrder() {
        return 100;
    }
}
