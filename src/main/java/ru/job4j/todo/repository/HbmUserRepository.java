package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmUserRepository implements UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(UserRepository.class.getName());

    private final SessionFactory sf;

    @Override
    public Optional<User> save(User user) {
        Session session = sf.openSession();
        Optional<User> rsl = Optional.empty();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            rsl = Optional.of(user);
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.warn("Объект user не был сохранен в БД по причине возникновения исключения: " + e.getMessage(), e);
        } finally {
            session.close();
        }
        return rsl;
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        Session session = sf.openSession();
        Optional<User> rsl = Optional.empty();
        try {
            session.beginTransaction();
            Query<User> query = session.createQuery(
                    "from User u where u.login = :fLogin and u.password = :fPassword", User.class);
            query.setParameter("fLogin", login);
            query.setParameter("fPassword", password);
            session.getTransaction().commit();
            rsl = query.uniqueResultOptional();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.warn("Возникло исключение при поиске записи в БД по login и password: " + e.getMessage(), e);
        } finally {
            session.close();
        }
        return rsl;
    }

    @Override
    public boolean deleteById(int userId) {
        Session session = sf.openSession();
        boolean rsl = false;
        try {
            session.beginTransaction();
            var affectedRows = session.createQuery(
                            "DELETE User WHERE id = :fUserId")
                    .setParameter("fUserId", userId)
                    .executeUpdate();
            session.getTransaction().commit();
            rsl = affectedRows > 0;
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.warn("Объект user не был удален из БД по причине возникновения исключения: " + e.getMessage(), e);
        } finally {
            session.close();
        }
        return rsl;
    }
}
