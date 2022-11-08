package ru.job4j.cinema.repository;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Seat;
import ru.job4j.cinema.service.SeatGridService;
import ru.job4j.cinema.service.SimpleSeatGridService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Seat persistence layer
 *  @author itfedorovsa (itfedorovsa@gmail.com)
 *  @since 03.11.22
 *  @version 1.0
 */
@ThreadSafe
@Repository
public class PostgresSeatRepository implements SeatRepository {
    private final DataSource pool;

    private final SeatGridService seatGridService;

    private static final String SELECT_OCCUPIED_SEATS = "SELECT seat_id FROM tickets WHERE session_id = ?";

    private static final Logger LOG = LogManager.getLogger(PostgresUserRepository.class.getName());

    public PostgresSeatRepository(BasicDataSource pool, SimpleSeatGridService seatGridService) {
        this.pool = pool;
        this.seatGridService = seatGridService;
    }

    /**
     * @param sessionId current movie id
     * @return list of remaining free seats in the cinema hall
     */
    public List<Seat> getFreeSeats(int sessionId) {
        List<Seat> dbSeats = new ArrayList<>();
        List<Seat> freeSeats = seatGridService.getAllSeats();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SELECT_OCCUPIED_SEATS)) {
            ps.setInt(1, sessionId);
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    dbSeats.add(seatGridService.findById(it.getInt("seat_id")));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in findOccupiedSeats()", e);
        }
        freeSeats.removeAll(dbSeats);
        return freeSeats;
    }
}
