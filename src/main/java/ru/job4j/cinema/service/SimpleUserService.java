package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.repository.UserRepository;

import java.util.List;
import java.util.Optional;

/**
 * User service layer
 *  @author itfedorovsa (itfedorovsa@gmail.com)
 *  @since 03.11.22
 *  @version 1.0
 */
@ThreadSafe
@Service
public class SimpleUserService implements UserService {
    private final UserRepository store;

    public SimpleUserService(UserRepository store) {
        this.store = store;
    }

    public Optional<User> add(User user) {
        return store.add(user);
    }

    public Optional<User> findByEmail(String email) {
        return store.findByEmail(email);
    }

    public Optional<User> findByEmailAndPhone(String email, String phone) {
        return store.findByEmailAndPhone(email, phone);
    }

    public Optional<User> findById(int id) {
        return store.findById(id);
    }

    public Optional<User> findByPhone(String phone) {
        return store.findByPhone(phone);
    }

    public void update(User user) {
        store.update(user);
    }

    public List<User> findAll() {
        return store.findAll();
    }
}
