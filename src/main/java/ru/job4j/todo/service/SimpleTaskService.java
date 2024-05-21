package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.TaskStore;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleTaskService implements TaskService {

    private final TaskStore taskStore;

    @Override
    public Optional<Task> add(Task task) {
        return taskStore.add(task);
    }

    @Override
    public boolean update(Integer taskId) {
        return taskStore.update(taskId);
    }

    @Override
    public boolean update(Task task) {
        return taskStore.update(task);
    }

    @Override
    public boolean delete(Integer taskId) {
        return taskStore.delete(taskId);
    }

    @Override
    public Optional<Task> findById(Integer taskId) {
        return taskStore.findById(taskId);
    }

    @Override
    public Collection<Task> findAll() {
        return taskStore.findAll();
    }

    @Override
    public Collection<Task> findByCurrentDate() {
        return taskStore.findByCurrentDate();
    }

    @Override
    public Collection<Task> findByDone() {
        return taskStore.findByDone();
    }
}
