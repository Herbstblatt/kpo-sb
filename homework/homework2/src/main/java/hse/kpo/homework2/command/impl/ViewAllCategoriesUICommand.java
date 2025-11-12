package hse.kpo.homework2.command.impl;

import hse.kpo.homework2.command.ConsoleCommand;
import hse.kpo.homework2.facade.CategoryFacade;
import hse.kpo.homework2.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class ViewAllCategoriesUICommand implements ConsoleCommand {

    private final CategoryFacade categoryFacade;

    @Override
    public String getKey() {
        return "view_categories";
    }

    @Override
    public String getDescription() {
        return "Просмотреть все категории";
    }

    @Override
    public int getOrder() {
        return -1;
    }

    @Override
    public boolean execute(Scanner scanner) {
        List<Category> categories = categoryFacade.getAllCategories();

        if (categories.isEmpty()) {
            System.out.println("\nУ вас пока нет категорий.");
            return true;
        }

        System.out.println("\n" + "═".repeat(70));
        System.out.println("ВАШИ КАТЕГОРИИ:");
        System.out.println("═".repeat(70));

        categories.forEach(c -> System.out.printf("  ID: %-5d | %s\n", c.getId(), c.getName()));

        System.out.println("═".repeat(70));

        return true;
    }
}
