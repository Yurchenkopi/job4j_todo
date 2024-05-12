package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.TaskFilter;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Repository
@AllArgsConstructor
public class HbmTaskFilterRepository implements TaskFilterRepository {
    private static final Logger LOG = LoggerFactory.getLogger(HbmTaskFilterRepository.class.getName());
    private final SessionFactory sf;

    @Override
    public Collection<TaskFilter> findAll() {
        Session session = sf.openSession();
        List<TaskFilter> rsl = Collections.emptyList();
        try {
            session.beginTransaction();
            var query = session.createQuery("from TaskFilter", TaskFilter.class);
            session.getTransaction().commit();
            rsl = query.list();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.warn("Возникло исключение при поиске всех записей в БД.");
        } finally {
            session.close();
        }
        return rsl;
    }
}
