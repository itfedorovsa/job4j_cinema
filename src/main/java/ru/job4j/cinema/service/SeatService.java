package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Seat;

import java.util.List;

/**
 * Seat service interface
 *  @author itfedorovsa (itfedorovsa@gmail.com)
 *  @since 03.11.22
 *  @version 1.0
 */
public interface SeatService {
    List<Seat> getFreeSeats(int sessionId);
}
