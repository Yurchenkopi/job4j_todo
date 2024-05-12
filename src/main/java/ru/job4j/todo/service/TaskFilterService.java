package ru.job4j.todo.service;

import ru.job4j.todo.model.TaskFilter;

import java.util.Collection;

public interface TaskFilterService {
    Collection<TaskFilter> findAll();
}
