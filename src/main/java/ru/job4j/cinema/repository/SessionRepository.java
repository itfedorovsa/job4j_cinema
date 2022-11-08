package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Session;

import java.util.List;
import java.util.Optional;

/**
 *  Session persistence interface
 *  @author itfedorovsa (itfedorovsa@gmail.com)
 *  @since 03.11.22
 *  @version 1.0
 */
public interface SessionRepository {
    Optional<Session> add(Session session);

    void update(Session session);

    Optional<Session> findById(int id);

    List<Session> findAll();
}
