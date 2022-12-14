package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Session;

import java.util.List;
import java.util.Optional;

/**
 * Session service interface
 *  @author itfedorovsa (itfedorovsa@gmail.com)
 *  @since 03.11.22
 *  @version 1.0
 */
public interface SessionService {
    Optional<Session> add(Session session);

    Optional<Session> findById(int id);

    void update(Session session);

    List<Session> findAll();
}
