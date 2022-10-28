package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.persistence.UserDBStore;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
public class UserService {
    private final UserDBStore store;

    public UserService(UserDBStore store) {
        this.store = store;
    }

    public Optional<User> add(User user) {
        return store.add(user);
    }

    public Optional<User> findByEmail(String email) {
        return store.findByEmail(email);
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
