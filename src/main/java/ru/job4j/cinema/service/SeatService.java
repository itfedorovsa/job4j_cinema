package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Seat;
import ru.job4j.cinema.persistence.SeatDBStore;

import java.util.List;

/**
 * Seat service layer
 *  * @author itfedorovsa (itfedorovsa@gmail.com)
 *  * @since 03.11.22
 *  * @version 1.0
 */
@ThreadSafe
@Service
public class SeatService {
    private final SeatDBStore seatDBStore;

    public SeatService(SeatDBStore seatDBStore) {
        this.seatDBStore = seatDBStore;
    }

    public List<Seat> getFreeSeats(int sessionId) {
        return seatDBStore.getFreeSeats(sessionId);
    }
}
