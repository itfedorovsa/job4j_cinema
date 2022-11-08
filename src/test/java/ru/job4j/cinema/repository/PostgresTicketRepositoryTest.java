package ru.job4j.cinema.repository;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.cinema.Main;
import ru.job4j.cinema.model.Seat;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.SimpleSeatGridService;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;

import static org.assertj.core.api.Assertions.*;

/**
 * Ticket persistence test class
 *  @author itfedorovsa (itfedorovsa@gmail.com)
 *  @since 09.11.22
 *  @version 1.0
 */
public class PostgresTicketRepositoryTest {

    private static Connection connection;

    @BeforeClass
    public static void initConnection() {
        try (InputStream in = PostgresTicketRepositoryTest.class.getClassLoader().getResourceAsStream("test.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            connection = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")

            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @AfterClass
    public static void closeConnection() throws SQLException {
        connection.close();
    }

    @After
    public void wipeTable() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM tickets; DELETE FROM users; ")) {
            statement.execute();
        }
    }

    /**
     *  Testing ticket adding
     */
    @Test
    public void addTicket() {
        TicketRepository ticketRep = new PostgresTicketRepository(new Main().loadPool(), new SimpleSeatGridService());
        Session sessionObj = new Session(2, "name", 1999, "desc");
        User userObj = new User(95, "name", "email", "phone");
        SimpleSeatGridService seatGrid = new SimpleSeatGridService();
        Seat seatObj = new Seat(3, seatGrid.findById(3).getRow(), seatGrid.findById(3).getCell());
        Optional<Session> session = new PostgresSessionRepository(
                new Main().loadPool()).add(sessionObj);
        assertThat(session).isPresent();
        Optional<User> user = new PostgresUserRepository(
                new Main().loadPool()).add(userObj);
        assertThat(user).isPresent();
        Ticket expected = new Ticket(1, session.get(), seatObj, userObj);
        Optional<Ticket> ticket = ticketRep.add(expected);
        assertThat(ticket).isPresent();
        assertThat(ticket.get()).isEqualTo(expected);
    }
}