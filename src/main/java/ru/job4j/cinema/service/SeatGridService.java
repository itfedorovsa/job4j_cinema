package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.persistence.SeatGridDBStore;

import java.util.ArrayList;
import java.util.List;

@ThreadSafe
@Service
public class SeatGridService {
    private final List<Integer> seats;
    private final SeatGridDBStore seatGridDBStore;

    public SeatGridService(SeatGridDBStore seatGridDBStore) {
        this.seatGridDBStore = seatGridDBStore;
        this.seats = List.of(1, 2, 3, 4, 5, 6, 7, 8);
    }

    public List<Integer> getAllSeats() {
        return new ArrayList<>(seats);
    }

    public List<Integer> getFreeSeats(int sessionId) {
        return seatGridDBStore.getFreeSeats(sessionId);
    }
}
