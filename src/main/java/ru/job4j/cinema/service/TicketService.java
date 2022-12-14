package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Ticket;

import java.util.List;
import java.util.Optional;

/**
 * Ticket service interface
 *  @author itfedorovsa (itfedorovsa@gmail.com)
 *  @since 03.11.22
 *  @version 1.0
 */
public interface TicketService {
    Optional<Ticket> add(Ticket ticket);

    Optional<Ticket> findById(int id);

    List<Ticket> findByUserId(int id);

    List<Ticket> findAll();
}
