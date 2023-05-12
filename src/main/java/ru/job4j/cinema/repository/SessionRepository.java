package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Session;

import java.util.List;
import java.util.Optional;

/**
 * Session persistence interface
 *
 * @author itfedorovsa (itfedorovsa@gmail.com)
 * @version 1.0
 * @since 03.11.22
 */
public interface SessionRepository {

    /**
     * Add session
     *
     * @param session {@link ru.job4j.cinema.model.Session}
     * @return {@link java.util.Optional<ru.job4j.cinema.model.Session>}
     */
    Optional<Session> add(Session session);

    /**
     * Update session
     *
     * @param session {@link ru.job4j.cinema.model.Session}
     */
    void update(Session session);

    /**
     * Find session by id
     *
     * @param id Session id
     * @return {@link java.util.Optional<ru.job4j.cinema.model.Session>}
     */
    Optional<Session> findById(int id);

    /**
     * Find all sessions
     *
     * @return {@link java.util.List<ru.job4j.cinema.model.Session>}
     */
    List<Session> findAll();
}
