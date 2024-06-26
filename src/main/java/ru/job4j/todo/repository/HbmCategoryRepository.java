package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.Collection;
import java.util.Map;

@Repository
@AllArgsConstructor
public class HbmCategoryRepository implements CategoryRepository {
    private final CrudRepository crudRepository;


    @Override
    public Collection<Category> findAll() {
        return crudRepository.query(
                "FROM Category", Category.class
        );
    }

    @Override
    public Collection<Category> findByIds(Collection<Integer> categoriesId) {
        return crudRepository.query(
                "FROM Category WHERE id IN :fIds", Category.class,
                Map.of("fIds", categoriesId)
        );
    }
}
