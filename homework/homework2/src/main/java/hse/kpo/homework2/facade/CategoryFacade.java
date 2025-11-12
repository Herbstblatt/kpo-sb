package hse.kpo.homework2.facade;

import hse.kpo.homework2.factory.EntityFactory;
import hse.kpo.homework2.model.Category;
import hse.kpo.homework2.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryFacade {
    private final CategoryRepository repository;
    private final EntityFactory factory;

    public Category createCategory(String name) {
        Category category = factory.createCategory(name);
        return repository.save(category);
    }

    public Category getCategory(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + id));
    }

    public List<Category> getAllCategories() {
        return repository.findAll();
    }

    public Category updateCategory(Long id, String name) {
        Category category = getCategory(id);
        if (name != null && !name.trim().isEmpty()) {
            category.setName(name);
        }
        return repository.save(category);
    }

    public void deleteCategory(Long id) {
        repository.deleteById(id);
    }

    public void deleteAll() {
        repository.clear();
    }

    public Category createCategoryWithId(Long id, String name) {
        Category category = factory.createCategory(name);
        category.setId(id);
        return repository.save(category);
    }
}