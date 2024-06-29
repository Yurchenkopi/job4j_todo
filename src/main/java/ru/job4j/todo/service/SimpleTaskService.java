package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.TaskStore;

import java.util.*;
import java.util.concurrent.Callable;

@Service
public class SimpleTaskService implements TaskService {

    private final TaskStore taskStore;

    private final CategoryService categoryService;

    private final Map<Integer, Callable<Collection<Task>>> filtersMap = new HashMap<>();

    public SimpleTaskService(TaskStore taskStore, CategoryService categoryService) {
        this.taskStore = taskStore;
        this.categoryService = categoryService;
        init();
    }

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

    @Override
    public Collection<Task> sort(Integer filterId) {
        Collection<Task> rsl = Collections.emptyList();
        if (filterId == null) {
            filterId = 1;
        }
        try {
            rsl = filtersMap.get(filterId).call();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    private void init() {
        filtersMap.put(1, taskStore::findAll);
        filtersMap.put(2, taskStore::findByCurrentDate);
        filtersMap.put(3, taskStore::findByDone);
    }
}
