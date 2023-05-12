package ru.job4j.cinema.repository;

import net.jcip.annotations.ThreadSafe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Seat;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.SeatGridService;
import ru.job4j.cinema.service.SimpleSeatGridService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * {@link ru.job4j.cinema.model.Ticket} persistence layer
 *
 * @author itfedorovsa (itfedorovsa@gmail.com)
 * @version 1.0
 * @since 03.11.22
 */
@ThreadSafe
@Repository
public class PostgresTicketRepository implements TicketRepository {

    private final DataSource pool;

    private final SeatGridService seatGridService;

    private static final String INSERT = "INSERT INTO tickets(session_id, seat_id, user_id) VALUES (?, ?, ?)";

    private static final String SELECT_ALL = """
            SELECT t.ticket_id, u.u_name, u.u_email, u.u_phone, s.s_name, t.seat_id
            FROM tickets t
            INNER JOIN sessions s ON t.session_id = s.s_id
            INNER JOIN users u ON t.user_id = u.u_id
            """;

    private static final String SELECT_USER_ID = """
            SELECT t.ticket_id, u.u_name, u.u_email, u.u_phone, s.s_name, t.seat_id, t.user_id
            FROM tickets t
            INNER JOIN sessions s ON t.session_id = s.s_id
            INNER JOIN users u ON t.user_id = u.u_id
            WHERE t.user_id = ?
            """;

    private static final String SELECT_TICKET_ID = """
            SELECT t.ticket_id, u.u_name, u.u_email, u.u_phone, s.s_name, t.seat_id, t.user_id
            FROM tickets t
            INNER JOIN sessions s ON t.session_id = s.s_id
            INNER JOIN users u ON t.user_id = u.u_id
            WHERE t.ticket_id = ?
            """;

    private static final Logger LOG = LogManager.getLogger(PostgresUserRepository.class.getName());

    public PostgresTicketRepository(DataSource source, SimpleSeatGridService seatGridService) {
        this.pool = source;
        this.seatGridService = seatGridService;
    }

    /**
     * Add ticket
     *
     * @param ticket {@link ru.job4j.cinema.model.Ticket}
     * @return {@link java.util.Optional<ru.job4j.cinema.model.Ticket>}
     */
    public Optional<Ticket> add(Ticket ticket) {
        Optional<Ticket> rsl = Optional.empty();
        try (Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, ticket.getSession().getSessionId());
            ps.setInt(2, ticket.getSeat().getSeatId());
            ps.setInt(3, ticket.getUser().getUserId());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    ticket.setTicketId(id.getInt(1));
                    rsl = Optional.of(ticket);
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in add()", e);
        }
        return rsl;
    }

    /**
     * Find ticket by id
     *
     * @param id Ticket id
     * @return {@link java.util.Optional<ru.job4j.cinema.model.Ticket>}
     */
    public Optional<Ticket> findById(int id) {
        Optional<Ticket> rsl = Optional.empty();
        try (Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement(SELECT_TICKET_ID)) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    rsl = Optional.of(newTicket(it));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in findById()", e);
        }
        return rsl;
    }

    /**
     * Find ticket by user id
     *
     * @param id user id
     * @return {@link java.util.List<ru.job4j.cinema.model.Ticket>}
     */
    public List<Ticket> findByUserId(int id) {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement(SELECT_USER_ID)) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    tickets.add(newTicket(it));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in findByUserId()", e);
        }
        return tickets;
    }

    /**
     * Find all tickets
     *
     * @return {@link java.util.List<ru.job4j.cinema.model.Ticket>}
     */
    public List<Ticket> findAll() {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement(SELECT_ALL)) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    tickets.add(newTicket(it));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in findAll()", e);
        }
        return tickets;
    }

    /**
     * Standalone method for creating {@link ru.job4j.cinema.model.Ticket} object
     *
     * @param rslSet query from DB
     * @return {@link ru.job4j.cinema.model.Ticket} with values from ResultSet received from query
     * @throws SQLException may be thrown during interaction with the DB
     */
    private Ticket newTicket(ResultSet rslSet) throws SQLException {
        int id = rslSet.getInt("seat_id");
        Seat seat = seatGridService.findById(id);
        if (seat == null) {
            seat = new Seat(-1, 0, 0);
        }
        return new Ticket(rslSet.getInt("ticket_id"), new Session(rslSet.getString("s_name")), new Seat(seat.getRow(), seat.getCell()), new User(rslSet.getString("u_name"), rslSet.getString("u_email"), rslSet.getString("u_phone")));
    }

}
