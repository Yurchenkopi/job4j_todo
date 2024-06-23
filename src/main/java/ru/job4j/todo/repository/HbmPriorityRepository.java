package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Priority;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmPriorityRepository implements PriorityRepository {
    private final CrudRepository crudRepository;

    @Override
    public Collection<Priority> findAll() {
        return crudRepository.query(
                "FROM Priority", Priority.class
        );
    }

    @Override
    public Optional<Priority> findById(Integer priorityId) {
        return crudRepository.optional(
                "FROM Priority WHERE id = :fId", Priority.class,
                Map.of("fId", priorityId)
        );
    }
}
