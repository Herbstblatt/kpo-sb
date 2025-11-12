package hse.kpo.homework2.command;

import java.util.Scanner;

public class TimingDecorator extends CommandDecorator {
    private final String commandName;

    public TimingDecorator(ConsoleCommand command, String commandName) {
        super(command);
        this.commandName = commandName;
    }

    @Override
    public boolean execute(Scanner scanner) {
        long startTime = System.nanoTime();
        
        boolean result = wrappedCommand.execute(scanner);
        
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000;
        
        System.out.println("[TIMING] Выполнение команды " + commandName + " заняло " + duration + " ms");
        return result;
    }
}
