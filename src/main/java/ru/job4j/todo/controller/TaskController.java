package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.TaskFilter;
import ru.job4j.todo.service.TaskFilterService;
import ru.job4j.todo.service.TaskService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    private final TaskFilterService taskFilterService;

    public TaskController(TaskService taskService, TaskFilterService taskFilterService) {
        this.taskService = taskService;
        this.taskFilterService = taskFilterService;
    }

    @GetMapping
    public String getAll(@RequestParam(name = "filterId", required = false) Integer filterId, Model model) {
        Collection<Task> data = Collections.emptyList();
        if (filterId == null) {
            filterId = 1;
        }
        if (filterId == 1) {
            data = taskService.findAll();
        } else if (filterId == 2) {
            data = taskService.findByCurrentDate();
        } else if (filterId == 3) {
            data = taskService.findByDone();
        }
        if (data.isEmpty()) {
            model.addAttribute("message", "Список дел пуст или произошла ошибка при сортировке.");
            return "errors/404";
        }
        model.addAttribute("tasks", data);
        model.addAttribute("filterId", filterId);
        model.addAttribute("filters", taskFilterService.findAll());
        return "tasks/list";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        var taskOptional = taskService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "Не удалось найти задачу по указанному Id.");
            return "errors/404";
        }
        model.addAttribute("task", taskOptional.get());
        return "tasks/one";
    }

    @GetMapping("/{id}/makeDone")
    public String makeDone(Model model, @PathVariable int id) {
        var isUpdated = taskService.update(id);
        if (!isUpdated) {
            model.addAttribute("message", "Не удалось перевести задачу в выполненные.");
            return "errors/404";
        }
        return "redirect:/tasks/{id}";
    }

    @GetMapping("/{id}/remove")
    public String remove(Model model, @PathVariable int id) {
        var isDeleted = taskService.delete(id);
        if (!isDeleted) {
            model.addAttribute("message", "Не удалось удалить задачу.");
            return "errors/404";
        }
        return "redirect:/tasks/";
    }

    @GetMapping("/{id}/update")
    public String updateStatus(Model model, @PathVariable int id) {
        var taskOptional = taskService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "Не удалось найти задачу по указанному Id.");
            return "errors/404";
        }
        model.addAttribute("task", taskOptional.get());
        return "tasks/edit";
    }

    @GetMapping("/create")
    public String create() {
        return "tasks/new";
    }

    @PostMapping("/{id}/update")
    public String updateTask(@ModelAttribute Task task, Model model) {
        var isUpdated = taskService.update(task);
        if (!isUpdated) {
            model.addAttribute("message", "Не удалось произвести редактирование задачи.");
            return "errors/404";
        }
        var message = String.format(
                "Задача с id=%s была успешно отредактирована.",
                task.getId());
        model.addAttribute("message", message);
        return "messages/message";
    }

    @PostMapping("/create")
    public String createTask(@ModelAttribute Task task, Model model) {
        System.out.println(task);
        var taskOptional = taskService.add(task);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "Не удалось создать новое задание.");
            return "errors/404";
        }
        var message = String.format(
                "Задача с id=%s была успешно отредактирована.",
                task.getId());
        model.addAttribute("message", message);
        return "messages/message";
    }
}
