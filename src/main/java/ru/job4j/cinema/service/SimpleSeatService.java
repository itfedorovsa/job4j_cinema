package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Seat;
import ru.job4j.cinema.repository.SeatRepository;

import java.util.List;

/**
 * Seat service layer
 *  @author itfedorovsa (itfedorovsa@gmail.com)
 *  @since 03.11.22
 *  @version 1.0
 */
@ThreadSafe
@Service
public class SimpleSeatService implements SeatService {
    private final SeatRepository postgresSeatRepository;

    public SimpleSeatService(SeatRepository postgresSeatRepository) {
        this.postgresSeatRepository = postgresSeatRepository;
    }

    public List<Seat> getFreeSeats(int sessionId) {
        return postgresSeatRepository.getFreeSeats(sessionId);
    }
}
