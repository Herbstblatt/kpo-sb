package hse.kpo.homework2.command.impl;

import hse.kpo.homework2.command.ConsoleCommand;
import hse.kpo.homework2.facade.CategoryFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class CreateCategoryUICommand implements ConsoleCommand {

    private final CategoryFacade categoryFacade;

    @Override
    public String getKey() {
        return "create_category";
    }

    @Override
    public String getDescription() {
        return "Создать новую категорию";
    }

    @Override
    public int getOrder() {
        return -1;
    }

    @Override
    public boolean execute(Scanner scanner) {
        System.out.print("Введите название категории: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Название не может быть пустым!");
            return true;
        }

        categoryFacade.createCategory(name);

        System.out.println("✓ Категория успешно создана!");
        return true;
    }
}
