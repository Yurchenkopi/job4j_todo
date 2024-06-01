package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.TaskFilter;

import java.util.Collection;

@Repository
@AllArgsConstructor
public class HbmTaskFilterRepository implements TaskFilterRepository {
    private final CrudRepository crudRepository;

    @Override
    public Collection<TaskFilter> findAll() {
        return crudRepository.query(
                "FROM TaskFilter", TaskFilter.class
        );
    }
}
