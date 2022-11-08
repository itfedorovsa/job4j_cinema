package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.repository.TicketRepository;

import java.util.List;
import java.util.Optional;

/**
 * Ticket service layer
 *  @author itfedorovsa (itfedorovsa@gmail.com)
 *  @since 03.11.22
 *  @version 1.0
 */
@ThreadSafe
@Service
public class SimpleTicketService implements TicketService {
    private final TicketRepository store;

    public SimpleTicketService(TicketRepository store) {
        this.store = store;
    }

    public Optional<Ticket> add(Ticket ticket) {
        return store.add(ticket);
    }

    public Optional<Ticket> findById(int id) {
        return store.findById(id);
    }

    public List<Ticket> findByUserId(int id) {
        return store.findByUserId(id);
    }

    public List<Ticket> findAll() {
        return store.findAll();
    }
}
