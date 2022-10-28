package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.persistence.TicketDBStore;
import ru.job4j.cinema.persistence.UserDBStore;

import java.util.*;

@ThreadSafe
@Service
public class TicketService {
    private final TicketDBStore store;

    public TicketService(TicketDBStore store) {
        this.store = store;
    }

    public Optional<Ticket> add(Ticket ticket) {
        return store.add(ticket);
    }

    public Optional<Ticket> findById(int id) {
        return store.findById(id);
    }

    /*public Optional<Ticket> findUserByEmailAndPhone(String email, String password) {
        return store.findUserByEmailAndPhone(email, password);
    }*/

    public List<Ticket> findAll() {
        return store.findAll();
    }
}
