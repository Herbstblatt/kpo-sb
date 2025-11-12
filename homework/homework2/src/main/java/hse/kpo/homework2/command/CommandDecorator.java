package hse.kpo.homework2.command;

import java.util.Scanner;

public abstract class CommandDecorator implements ConsoleCommand {
    protected ConsoleCommand wrappedCommand;

    public CommandDecorator(ConsoleCommand command) {
        this.wrappedCommand = command;
    }

    public String getKey() {
        return  wrappedCommand.getKey();
    }

    public String getDescription() {
        return  wrappedCommand.getDescription();
    }

    @Override
    public int getOrder() {
        return wrappedCommand.getOrder();
    }

    @Override
    public boolean execute(Scanner scanner) {
        return wrappedCommand.execute(scanner);
    }
}
