package ru.job4j.cinema.model;

import java.util.Objects;

/**
 * Ticket data model
 *  @author itfedorovsa (itfedorovsa@gmail.com)
 *  @since 03.11.22
 *  @version 1.0
 */
public class Ticket {

    /**
     *  Ticket id.
     */
    private int ticketId;

    /**
     *  Session object with its fields.
     */
    private Session session;

    /**
     *  Seat object with its fields.
     */
    private Seat seat;

    /**
     *  User object with its fields.
     */
    private User user;

    public Ticket() {
    }

    public Ticket(int ticketId, Session session, Seat seat, User user) {
        this.ticketId = ticketId;
        this.session = session;
        this.seat = seat;
        this.user = user;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ticket ticket = (Ticket) o;
        return ticketId == ticket.ticketId && Objects.equals(session, ticket.session) && Objects.equals(seat, ticket.seat) && Objects.equals(user, ticket.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketId, session, seat, user);
    }

    @Override
    public String toString() {
        return "Ticket{"
                + "id=" + ticketId
                + ", session=" + session
                + ", seat=" + seat
                + ", user=" + user
                + '}';
    }
}
