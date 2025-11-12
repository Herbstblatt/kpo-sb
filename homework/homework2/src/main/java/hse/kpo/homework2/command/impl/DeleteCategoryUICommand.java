package hse.kpo.homework2.command.impl;

import hse.kpo.homework2.command.ConsoleCommand;
import hse.kpo.homework2.facade.CategoryFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class DeleteCategoryUICommand implements ConsoleCommand {

    private final CategoryFacade categoryFacade;
    private final ViewAllCategoriesUICommand viewAllCategoriesUICommand;

    @Override
    public String getKey() {
        return "delete_category";
    }

    @Override
    public String getDescription() {
        return "Удалить категорию";
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

        System.out.print("\nВведите ID категории для удаления: ");
        Long id;
        try {
            id = Long.parseLong(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат ID!");
            return true;
        }

        categoryFacade.deleteCategory(id);

        System.out.println("✓ Категория успешно удалена!");
        return true;
    }
}
