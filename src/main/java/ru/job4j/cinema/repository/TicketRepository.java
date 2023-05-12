package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Ticket;

import java.util.List;
import java.util.Optional;

/**
 * Ticket persistence interface
 *
 * @author itfedorovsa (itfedorovsa@gmail.com)
 * @version 1.0
 * @since 03.11.22
 */
public interface TicketRepository {

    /**
     * Add ticket
     *
     * @param ticket {@link ru.job4j.cinema.model.Ticket}
     * @return {@link java.util.Optional<ru.job4j.cinema.model.Ticket>}
     */
    Optional<Ticket> add(Ticket ticket);

    /**
     * Find ticket by id
     *
     * @param id Ticket id
     * @return {@link java.util.Optional<ru.job4j.cinema.model.Ticket>}
     */
    Optional<Ticket> findById(int id);

    /**
     * Find ticket by user id
     *
     * @param id user id
     * @return {@link java.util.List<ru.job4j.cinema.model.Ticket>}
     */
    List<Ticket> findByUserId(int id);

    /**
     * Find all tickets
     *
     * @return {@link java.util.List<ru.job4j.cinema.model.Ticket>}
     */
    List<Ticket> findAll();
}
