package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.TaskFilter;
import ru.job4j.todo.repository.TaskFilterRepository;

import java.util.Collection;

@Service
@AllArgsConstructor
public class SimpleTaskFilterService implements TaskFilterService {
    private final TaskFilterRepository taskFilterRepository;

    @Override
    public Collection<TaskFilter> findAll() {
        return taskFilterRepository.findAll();
    }
}
