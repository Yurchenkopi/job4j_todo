package ru.job4j.todo.repository;

import ru.job4j.todo.model.TaskFilter;

import java.util.Collection;

public interface TaskFilterRepository {
    Collection<TaskFilter> findAll();
}
