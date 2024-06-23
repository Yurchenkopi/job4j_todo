package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.time.LocalDateTime;
import java.util.*;

@Repository
@AllArgsConstructor
public class TaskStore implements Store {

    private static final Logger LOG = LoggerFactory.getLogger(TaskStore.class.getName());
    private final CrudRepository crudRepository;

    @Override
    public Optional<Task> add(Task task) {
        try {
            crudRepository.run(session -> session.save(task));
            return Optional.of(task);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public boolean update(Integer taskId) {
        return crudRepository.runAndReturnBool(
                """
                    UPDATE Task
                    SET
                    done = :fDone
                    WHERE id = :fId
                    """,
                Map.of(
                        "fDone", true,
                        "fId", taskId
                )
        );
    }

    @Override
    public boolean update(Task task) {
        return crudRepository.runAndReturnBool(
                """
                    UPDATE Task
                    SET
                    description = :fDescription,
                    priority = :fPriority
                    WHERE id = :fId
                    """,
                Map.of(
                        "fDescription", task.getDescription(),
                        "fPriority", task.getPriority(),
                        "fId", task.getId()
                )
        );
    }

    @Override
    public boolean delete(Integer taskId) {
        return crudRepository.runAndReturnBool(
                "DELETE Task WHERE id = :fId",
                Map.of(
                        "fId", taskId
                )
        );
    }

    @Override
    public Optional<Task> findById(Integer taskId) {
        return crudRepository.optional(
                "FROM Task t JOIN FETCH t.priority WHERE t.id = :fId", Task.class,
                Map.of(
                        "fId", taskId
                )
        );
    }

    @Override
    public Collection<Task> findAll() {
        return crudRepository.query(
                "FROM Task t JOIN FETCH t.priority", Task.class
        );
    }

    @Override
    public Collection<Task> findByCurrentDate() {
        return crudRepository.query(
                """
            FROM Task t JOIN FETCH t.priority
            WHERE created BETWEEN :fStartDateTime AND :fEndDateTime
            """,
                Task.class,
                Map.of(
                        "fStartDateTime", LocalDateTime.now().minusDays(5),
                        "fEndDateTime", LocalDateTime.now()
                )
        );
    }

    @Override
    public Collection<Task> findByDone() {
        return crudRepository.query(
                "FROM Task t JOIN FETCH t.priority WHERE done = TRUE", Task.class
        );
    }
}