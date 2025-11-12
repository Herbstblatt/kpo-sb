package hse.kpo.homework2.command;

import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class CommandRegistry {
    private final Map<String, ConsoleCommand> commands = new LinkedHashMap<>();
    private final List<ConsoleCommand> orderedCommands = new ArrayList<>();

    public CommandRegistry(List<ConsoleCommand> commandList) {
        for (ConsoleCommand command : commandList) {
            commands.put(command.getKey().toLowerCase(), command);
        }

        orderedCommands.addAll(commandList);
        orderedCommands.sort(Comparator.comparingInt(ConsoleCommand::getOrder));
    }

    public Optional<ConsoleCommand> getCommand(String key) {
        return Optional.ofNullable(commands.get(key.toLowerCase()));
    }

    public List<ConsoleCommand> getShowableCommands() {
        return orderedCommands.stream().filter(c -> c.getOrder() >= 0).toList();
    }

    public String formatMenu() {
        StringBuilder sb = new StringBuilder("\n=== МЕНЮ ===\n");
        for (ConsoleCommand command : getShowableCommands()) {
            sb.append(command.getKey())
                    .append(". ")
                    .append(command.getDescription())
                    .append("\n");
        }
        return sb.toString();
    }
}