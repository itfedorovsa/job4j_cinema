package ru.job4j.cinema.repository;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Session;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Session persistence layer
 *
 * @author itfedorovsa (itfedorovsa@gmail.com)
 * @version 1.0
 * @since 03.11.22
 */
@ThreadSafe
@Repository
public class PostgresSessionRepository implements SessionRepository {

    private final DataSource pool;

    private static final String INSERT = "INSERT INTO sessions(s_name, s_year, s_description) VALUES (?, ?, ?)";

    private static final String SELECT_ALL = "SELECT * FROM sessions";

    private static final String UPDATE = "UPDATE sessions SET s_name = ?, s_year = ? WHERE s_description = ?";

    private static final String SELECT_ID = "SELECT * FROM sessions WHERE s_id = ?";

    private static final Logger LOG = LogManager.getLogger(PostgresUserRepository.class.getName());

    public PostgresSessionRepository(BasicDataSource pool) {
        this.pool = pool;
    }

    /**
     * Add session
     *
     * @param session {@link ru.job4j.cinema.model.Session}
     * @return {@link java.util.Optional<ru.job4j.cinema.model.Session>}
     */
    public Optional<Session> add(Session session) {
        Optional<Session> rsl = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, session.getName());
            ps.setInt(2, session.getYear());
            ps.setString(3, session.getDescription());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    session.setSessionId(id.getInt(1));
                    rsl = Optional.of(session);
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in add()", e);
        }
        return rsl;
    }

    /**
     * Update session
     *
     * @param session {@link ru.job4j.cinema.model.Session}
     */
    public void update(Session session) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(UPDATE)) {
            ps.setString(1, session.getName());
            ps.setInt(2, session.getYear());
            ps.setString(3, session.getDescription());
            ps.execute();
        } catch (Exception e) {
            LOG.error("Exception in update()", e);
        }
    }

    /**
     * Find session by id
     *
     * @param id Session id
     * @return {@link java.util.Optional<ru.job4j.cinema.model.Session>}
     */
    public Optional<Session> findById(int id) {
        Optional<Session> rsl = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(SELECT_ID)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    rsl = Optional.of(newSession(it));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in findById()", e);
        }
        return rsl;
    }

    /**
     * Find all sessions
     *
     * @return {@link java.util.List<ru.job4j.cinema.model.Session>}
     */
    public List<Session> findAll() {
        List<Session> sessions = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(SELECT_ALL)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    sessions.add(newSession(it));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in findAll()", e);
        }
        return sessions;
    }

    /**
     * Standalone method for creating {@link ru.job4j.cinema.model.Session} object
     *
     * @param rslSet query from DB
     * @return Session with values from ResultSet received from query
     * @throws SQLException may be thrown during interaction with the DB
     */
    private Session newSession(ResultSet rslSet) throws SQLException {
        return new Session(rslSet.getInt("s_id"),
                rslSet.getString("s_name"),
                rslSet.getInt("s_year"),
                rslSet.getString("s_description"));
    }

}
