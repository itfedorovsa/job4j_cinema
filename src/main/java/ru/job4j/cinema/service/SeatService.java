package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Seat;

import java.util.*;

@ThreadSafe
@Service
public class SeatService {
    private Map<Integer, Seat> seats = new HashMap<>();

    public SeatService() {
        seats.put(1, new Seat(1, 1, 1));
        seats.put(2, new Seat(2, 1, 2));
        seats.put(3, new Seat(3, 1, 3));
        seats.put(4, new Seat(4, 1, 4));
        seats.put(5, new Seat(5, 2, 1));
        seats.put(6, new Seat(6, 2, 2));
        seats.put(7, new Seat(7, 2, 3));
        seats.put(8, new Seat(8, 2, 4));

    }

    public List<Seat> getAllSeats() {
        return new ArrayList<>(seats.values());
    }

    public Seat findById(int id) {
        return seats.get(id);
    }
}
