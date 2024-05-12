package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.TaskFilter;
import ru.job4j.todo.repository.TaskFilterRepository;

import java.util.Collection;

@Service
public class SimpleTaskFilterService implements TaskFilterService {
    private final TaskFilterRepository taskFilterRepository;

    public SimpleTaskFilterService(TaskFilterRepository hbmTaskFilterRepository) {
        this.taskFilterRepository = hbmTaskFilterRepository;
    }

    @Override
    public Collection<TaskFilter> findAll() {
        return taskFilterRepository.findAll();
    }
}
