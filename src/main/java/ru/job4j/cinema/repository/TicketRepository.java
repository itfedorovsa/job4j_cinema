package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Ticket;

import java.util.List;
import java.util.Optional;

/**
 *  Ticket persistence interface
 *  @author itfedorovsa (itfedorovsa@gmail.com)
 *  @since 03.11.22
 *  @version 1.0
 */
public interface TicketRepository {
    Optional<Ticket> add(Ticket ticket);

    Optional<Ticket> findById(int id);

    List<Ticket> findByUserId(int id);

    List<Ticket> findAll();
}
