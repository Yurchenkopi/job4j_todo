package ru.job4j.todo.repository;

import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Optional;

public interface Store {
    Optional<Task> add(Task task);

    boolean update(Integer taskId);

    boolean update(Task task);

    boolean updateTaskCategories(Task task, Category category);

    boolean deleteTaskCategories(Task task);

    boolean delete(Integer taskId);

    Optional<Task> findById(Integer taskId);

    Collection<Task> findAll();

    Collection<Task> findByCurrentDate();

    Collection<Task> findByDone();
}
