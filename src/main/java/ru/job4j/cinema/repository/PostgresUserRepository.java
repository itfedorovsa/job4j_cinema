package ru.job4j.cinema.repository;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * User persistence layer
 *  @author itfedorovsa (itfedorovsa@gmail.com)
 *  @since 03.11.22
 *  @version 1.0
 */
@ThreadSafe
@Repository
public class PostgresUserRepository implements UserRepository {
    private final DataSource pool;
    private static final String INSERT = "INSERT INTO users(u_name, u_email, u_phone) VALUES (?, ?, ?)";
    private static final String SELECT_ALL = "SELECT * FROM users";
    private static final String UPDATE = "UPDATE users SET u_name = ?, u_email = ?, u_phone = ? WHERE u_id = ?";
    private static final String SELECT_EMAIL_AND_PHONE = "SELECT * FROM users WHERE u_email = ? AND u_phone = ?";
    private static final String SELECT_EMAIL = "SELECT * FROM users WHERE u_email = ?";
    private static final String SELECT_ID = "SELECT * FROM users WHERE u_id = ?";
    private static final String SELECT_PHONE = "SELECT * FROM users WHERE u_phone = ?";
    private static final Logger LOG = LogManager.getLogger(PostgresUserRepository.class.getName());

    public PostgresUserRepository(BasicDataSource pool) {
        this.pool = pool;
    }

    public Optional<User> add(User user) {
        Optional<User> rsl = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setUserId(id.getInt(1));
                    rsl = Optional.of(user);
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in add()", e);
        }
        return rsl;
    }

    public void update(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(UPDATE)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setInt(4, user.getUserId());
            ps.execute();
        } catch (Exception e) {
            LOG.error("Exception in update()", e);
        }
    }

    public Optional<User> findByEmail(String email) {
        Optional<User> rsl = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SELECT_EMAIL)
        ) {
            ps.setString(1, email);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    rsl = Optional.of(newUser(it));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in findByEmail()", e);
        }
        return rsl;
    }

    public Optional<User> findById(int id) {
        Optional<User> rsl = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SELECT_ID)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    rsl = Optional.of(newUser(it));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in findById()", e);
        }
        return rsl;
    }

    public Optional<User> findByPhone(String phone) {
        Optional<User> rsl = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SELECT_PHONE)
        ) {
            ps.setString(1, phone);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    rsl = Optional.of(newUser(it));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in findByPhone()", e);
        }
        return rsl;
    }

    public Optional<User> findByEmailAndPhone(String email, String phone) {
        Optional<User> rsl = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SELECT_EMAIL_AND_PHONE)
        ) {
            ps.setString(1, email);
            ps.setString(2, phone);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    rsl = Optional.of(newUser(it));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in findByEmailAndPhone()", e);
        }
        return rsl;
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SELECT_ALL)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    users.add(newUser(it));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in findAll()", e);
        }
        return users;
    }

    /**
     * Standalone method for creating User object
     * @param rslSet query from DB
     * @return User with values from ResultSet received from query
     * @throws SQLException may be thrown during interaction with the DB
     */
    private User newUser(ResultSet rslSet) throws SQLException {
        return new User(rslSet.getInt("u_id"),
                rslSet.getString("u_name"),
                rslSet.getString("u_email"),
                rslSet.getString("u_phone"));
    }
}
