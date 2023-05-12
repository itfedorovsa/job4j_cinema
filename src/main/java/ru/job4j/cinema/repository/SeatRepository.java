package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Seat;

import java.util.List;

/**
 * Seat persistence interface
 *
 * @author itfedorovsa (itfedorovsa@gmail.com)
 * @version 1.0
 * @since 03.11.22
 */
public interface SeatRepository {

    /**
     * Get free seats
     *
     * @param sessionId current movie (Session) id
     * @return list of remaining free seats in the cinema hall. Type {@link java.util.List<ru.job4j.cinema.model.Seat>}
     */
    List<Seat> getFreeSeats(int sessionId);
}
