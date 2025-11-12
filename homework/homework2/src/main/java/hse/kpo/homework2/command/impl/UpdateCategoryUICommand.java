package hse.kpo.homework2.command.impl;

import hse.kpo.homework2.command.ConsoleCommand;
import hse.kpo.homework2.facade.CategoryFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class UpdateCategoryUICommand implements ConsoleCommand {

    private final CategoryFacade categoryFacade;
    private final ViewAllCategoriesUICommand viewAllCategoriesUICommand;

    @Override
    public String getKey() {
        return "update_category";
    }

    @Override
    public String getDescription() {
        return "Изменить название категории";
    }

    @Override
    public int getOrder() {
        return -1;
    }

    @Override
    public boolean execute(Scanner scanner) {
        viewAllCategoriesUICommand.execute(scanner);

        if (categoryFacade.getAllCategories().isEmpty()) {
            return true;
        }

        System.out.print("\nВведите ID категории для изменения: ");
        Long id;
        try {
            id = Long.parseLong(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат ID!");
            return true;
        }

        System.out.print("Введите новое название: ");
        String newName = scanner.nextLine().trim();

        if (newName.isEmpty()) {
            System.out.println("Название не может быть пустым!");
            return true;
        }

        categoryFacade.updateCategory(id, newName);

        System.out.println("✓ Категория успешно обновлена!");
        return true;
    }
}
