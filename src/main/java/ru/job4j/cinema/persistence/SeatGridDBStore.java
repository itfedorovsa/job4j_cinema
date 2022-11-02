package ru.job4j.cinema.persistence;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.service.SeatGridService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@ThreadSafe
@Repository
public class SeatGridDBStore {
    private final BasicDataSource pool;
    private final SeatGridService seatGridService;

    private static final String SELECT_OCCUPIED_SEATS = "SELECT seat_id FROM tickets WHERE session_id = ?";

    private static final Logger LOG = LogManager.getLogger(UserDBStore.class.getName());

    public SeatGridDBStore(BasicDataSource pool, SeatGridService seatGridService) {
        this.pool = pool;
        this.seatGridService = seatGridService;
    }

    public List<Integer> getFreeSeats(int sessionId) {
        List<Integer> dbTickets = new ArrayList<>();
        List<Integer> freeSeats = seatGridService.getAllSeats();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SELECT_OCCUPIED_SEATS)) {
            ps.setInt(1, sessionId);
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    dbTickets.add(it.getInt("seat_id"));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in findOccupiedSeats()", e);
        }
        freeSeats.removeAll(dbTickets);
        return freeSeats;
    }
}
