package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmUserRepository implements UserRepository {
    private final CrudRepository crudRepository;

    @Override
    public Optional<User> save(User user) {
        crudRepository.run(session -> session.save(user));
        return Optional.of(user);
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return crudRepository.optional(
                "FROM User u WHERE u.login = :fLogin AND u.password = :fPassword", User.class,
                Map.of(
                        "fLogin", login,
                        "fPassword", password)
        );
    }

    @Override
    public boolean deleteById(int userId) {
        return crudRepository.runAndReturnBool(
                "DELETE User WHERE id = :fUserId",
                Map.of("fUserId", userId)
        );
    }
}
