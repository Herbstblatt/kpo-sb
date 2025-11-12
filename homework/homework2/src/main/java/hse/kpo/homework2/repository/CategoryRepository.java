package hse.kpo.homework2.repository;

import hse.kpo.homework2.model.Category;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class CategoryRepository {
    private final Map<Long, Category> storage = new HashMap<>();
    private Long nextId = 1L;

    public Category save(Category category) {
        if (category.getId() == null) {
            category.setId(nextId++);
        }
        storage.put(category.getId(), category);
        return category;
    }

    public Optional<Category> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    public List<Category> findAll() {
        return List.copyOf(storage.values());
    }

    public void deleteById(Long id) {
        storage.remove(id);
    }

    public void clear() {
        storage.clear();
        nextId = 1L;
    }
}
