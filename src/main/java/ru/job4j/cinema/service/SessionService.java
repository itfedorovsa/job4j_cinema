package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.persistence.SessionDBStore;

import java.util.List;
import java.util.Optional;

/**
 * Session service layer
 *  * @author itfedorovsa (itfedorovsa@gmail.com)
 *  * @since 03.11.22
 *  * @version 1.0
 */
@ThreadSafe
@Service
public class SessionService {
    private final SessionDBStore store;

    public SessionService(SessionDBStore store) {
        this.store = store;
    }

    public Optional<Session> add(Session session) {
        return store.add(session);
    }

    public Optional<Session> findById(int id) {
        return store.findById(id);
    }

    public void update(Session session) {
        store.update(session);
    }

    public List<Session> findAll() {
        return store.findAll();
    }
}
