package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Optional;

public interface TaskService {
    Optional<Task> add(Task task);

    boolean update(Integer taskId);

    boolean update(Task task);

    boolean delete(Integer taskId);

    Optional<Task> findById(Integer taskId);

    Collection<Task> findAll();

    Collection<Task> findByCurrentDate();

    Collection<Task> findByDone();

    Collection<Task> sort(Integer filterId);
}
