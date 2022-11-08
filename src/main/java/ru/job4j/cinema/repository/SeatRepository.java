package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Seat;

import java.util.List;

/**
 *  Seat persistence interface
 *  @author itfedorovsa (itfedorovsa@gmail.com)
 *  @since 03.11.22
 *  @version 1.0
 */
public interface SeatRepository {
    List<Seat> getFreeSeats(int sessionId);
}
