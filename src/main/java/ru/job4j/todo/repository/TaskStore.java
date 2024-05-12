package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TaskStore implements Store {

    private static final Logger LOG = LoggerFactory.getLogger(TaskStore.class.getName());
    private final SessionFactory sf;

    @Override
    public Optional<Task> add(Task task) {
        Session session = sf.openSession();
        Optional<Task> rsl = Optional.empty();
        try {
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
            rsl = Optional.of(task);
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.warn("Объект task не был сохранен в БД по причине возникновения исключения." + e.getMessage(), e);
        } finally {
            session.close();
        }
        return rsl;
    }

    @Override
    public boolean update(Integer taskId) {
        Session session = sf.openSession();
        boolean rsl = false;
        try {
            session.beginTransaction();
            int affectedRows = session.createQuery("""
                    UPDATE Task
                    SET
                    done = :fDone
                    WHERE id = :fId
                    """)
                    .setParameter("fDone", true)
                    .setParameter("fId", taskId)
                    .executeUpdate();
            session.getTransaction().commit();
            rsl = affectedRows > 0;
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return rsl;
    }

    @Override
    public boolean update(Task task) {
        Session session = sf.openSession();
        boolean rsl = false;
        try {
            session.beginTransaction();
            int affectedRows = session.createQuery("""
                    UPDATE Task
                    SET
                    description = :fDescription,
                    created = :fCreated,
                    done = :fDone
                    WHERE id = :fId
                    """)
                    .setParameter("fDescription", task.getDescription())
                    .setParameter("fCreated", task.getCreated())
                    .setParameter("fDone", task.isDone())
                    .setParameter("fId", task.getId())
                    .executeUpdate();
            session.getTransaction().commit();
            rsl = affectedRows > 0;
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return rsl;
    }

    @Override
    public boolean delete(Integer taskId) {
        Session session = sf.openSession();
        boolean rsl = false;
        try {
            session.beginTransaction();
            var affectedRows = session.createQuery(
                            "DELETE Task WHERE id = :fId")
                    .setParameter("fId", taskId)
                    .executeUpdate();
            session.getTransaction().commit();
            rsl = affectedRows > 0;
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.warn("Объект task не был удален из БД по причине возникновения исключения.");
        } finally {
            session.close();
        }
        return rsl;
    }

    @Override
    public Optional<Task> findById(Integer taskId) {
        Session session = sf.openSession();
        Optional<Task> rsl = Optional.empty();
        try {
            session.beginTransaction();
            Query<Task> query = session.createQuery(
                    "from Task t where t.id = :fId", Task.class);
            query.setParameter("fId", taskId);
            session.getTransaction().commit();
            rsl = query.uniqueResultOptional();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.warn("Возникло исключение при поиске записи в БД по id.");
        } finally {
            session.close();
        }
        return rsl;
    }

    @Override
    public Collection<Task> findAll() {
        Session session = sf.openSession();
        List<Task> rsl = Collections.emptyList();
        try {
            session.beginTransaction();
            var query = session.createQuery("from Task", Task.class);
            rsl = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.warn("Возникло исключение при поиске всех записей в БД.");
        } finally {
            session.close();
        }
        return rsl;
    }

    @Override
    public Collection<Task> findByCurrentDate() {
        Session session = sf.openSession();
        List<Task> rsl = Collections.emptyList();
        try {
            session.beginTransaction();
            var query = session.createQuery(
            """
            FROM Task
            WHERE created BETWEEN :fStartDateTime AND :fEndDateTime
            """,
                    Task.class)
                    .setParameter("fStartDateTime", LocalDateTime.now().minusDays(5))
                    .setParameter("fEndDateTime", LocalDateTime.now());
            rsl = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.warn("Возникло исключение при поиске всех записей в БД по фильтру новые.");
        } finally {
            session.close();
        }
        return rsl;
    }

    @Override
    public Collection<Task> findByDone() {
        Session session = sf.openSession();
        List<Task> rsl = Collections.emptyList();
        try {
            session.beginTransaction();
            var query = session.createQuery("FROM Task WHERE done = TRUE", Task.class);
            rsl = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.warn("Возникло исключение при поиске записей в БД по фильтру done.");
        } finally {
            session.close();
        }
        return rsl;
    }
}