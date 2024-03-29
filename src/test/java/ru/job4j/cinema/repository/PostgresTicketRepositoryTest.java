package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.cinema.model.Seat;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.SimpleSeatGridService;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Ticket persistence test class
 *
 * @author itfedorovsa (itfedorovsa@gmail.com)
 * @version 1.0
 * @since 09.11.22
 */
public class PostgresTicketRepositoryTest {

    private static BasicDataSource dataSource = new BasicDataSource();

    @BeforeClass
    public static void initConnection() {
        try (InputStream in = PostgresTicketRepositoryTest.class.getClassLoader().getResourceAsStream("test.properties")) {
            Properties config = new Properties();
            config.load(in);
            dataSource.setUrl(config.getProperty("url"));
            dataSource.setUsername(config.getProperty("username"));
            dataSource.setPassword(config.getProperty("password"));
            dataSource.setDriverClassName(config.getProperty("driver-class-name"));
            Class.forName(config.getProperty("driver-class-name"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @AfterClass
    public static void closeConnection() throws SQLException {
        dataSource.close();
    }

    @After
    public void wipeTable() throws SQLException {
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(
                "DELETE FROM tickets; DELETE FROM users;")) {
            statement.execute();
        }
    }

    /**
     * Ticket adding test
     */
    @Test
    public void whenAddTicket() {
        TicketRepository ticketRep = new PostgresTicketRepository(dataSource, new SimpleSeatGridService());
        Session sessionObj = new Session(2, "name", 1999, "desc");
        Optional<Session> session = new PostgresSessionRepository(
                dataSource).add(sessionObj);
        assertTrue(session.isPresent());
        User userObj = new User(95, "name", "email13", "phone13");
        Optional<User> user = new PostgresUserRepository(
                dataSource).add(userObj);
        assertTrue(user.isPresent());
        SimpleSeatGridService seatGrid = new SimpleSeatGridService();
        Seat seatObj = new Seat(3, seatGrid.findById(3).getRow(), seatGrid.findById(3).getCell());
        Ticket expected = new Ticket(1, session.get(), seatObj, user.get());
        Optional<Ticket> ticket = ticketRep.add(expected);
        assertTrue(ticket.isPresent());
        assertEquals(ticket.get(), expected);
    }

    /**
     * Searching by user id test
     */
    @Test
    public void whenFindTicketByUserId() {
        TicketRepository ticketRep = new PostgresTicketRepository(dataSource, new SimpleSeatGridService());
        Session sessionObj = new Session(2, "name", 1999, "desc");
        Optional<Session> session = new PostgresSessionRepository(
                dataSource).add(sessionObj);
        assertTrue(session.isPresent());
        User userObj = new User("name", "email19", "phone19");
        Optional<User> user = new PostgresUserRepository(
                dataSource).add(userObj);
        assertTrue(user.isPresent());
        SimpleSeatGridService seatGrid = new SimpleSeatGridService();
        Seat seatObj = new Seat(3, seatGrid.findById(3).getRow(), seatGrid.findById(3).getCell());
        Ticket baseTicket = new Ticket(5, session.get(), seatObj, userObj);
        Optional<Ticket> ticket = ticketRep.add(baseTicket);
        assertTrue(ticket.isPresent());
        List<Ticket> extracted = ticketRep.findByUserId(user.get().getUserId());
        assertEquals(1, extracted.size());
        assertEquals("name", extracted.get(0).getSession().getName());
    }

    /**
     * All tickets searching test
     */
    @Test
    public void whenFindAllTickets() {
        TicketRepository ticketRep = new PostgresTicketRepository(dataSource, new SimpleSeatGridService());
        Session sessionObj = new Session(2, "name", 1999, "desc");
        Optional<Session> session = new PostgresSessionRepository(
                dataSource).add(sessionObj);
        assertTrue(session.isPresent());
        User userObj = new User("name", "email21", "phone21");
        Optional<User> user = new PostgresUserRepository(
                dataSource).add(userObj);
        assertTrue(user.isPresent());
        SimpleSeatGridService seatGrid = new SimpleSeatGridService();
        Seat seatObj = new Seat(3, seatGrid.findById(3).getRow(), seatGrid.findById(3).getCell());
        Ticket baseTicket = new Ticket(5, session.get(), seatObj, userObj);
        Optional<Ticket> ticket = ticketRep.add(baseTicket);
        assertTrue(ticket.isPresent());
        List<Ticket> extracted = ticketRep.findAll();
        assertEquals(1, extracted.size());
        assertEquals("name", extracted.get(0).getSession().getName());
    }

}