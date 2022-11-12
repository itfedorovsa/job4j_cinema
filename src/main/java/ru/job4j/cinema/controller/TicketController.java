package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.cinema.model.Seat;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * TicketController
 * @author itfedorovsa (itfedorovsa@gmail.com)
 * @since 03.11.22
 * @version 1.0
 */
@ThreadSafe
@Controller
public class TicketController {
    private final SessionService sessionService;
    private final SeatGridService seatGridService;
    private final SeatService seatService;
    private final TicketService ticketService;
    private final UserService userService;

    public TicketController(SessionService sessionService, SeatGridService seatGridService,
                            SeatService seatService, TicketService ticketService, UserService userService) {
        this.sessionService = sessionService;
        this.seatGridService = seatGridService;
        this.seatService = seatService;
        this.ticketService = ticketService;
        this.userService = userService;
    }

    /**
     * Tickets page
     * @param model Model
     * @param httpSession HTTPSession
     * @return tickets.html - all purchased tickets by current user
     */
    @GetMapping("/tickets")
    public String tickets(Model model, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        model.addAttribute("movies", sessionService.findAll());
        model.addAttribute("seats", seatGridService.getAllSeats());
        model.addAttribute("tickets", ticketService.findByUserId(user.getUserId()));
        model.addAttribute("user", getUser(httpSession));
        return "ticket/tickets";
    }

    /**
     * Order fail page
     * @param model Model
     * @param httpSession HTTPSession
     * @return orderFail.html - page-warning about the inability to buy a ticket
     */
    @GetMapping("/orderFail")
    public String orderFail(Model model, HttpSession httpSession) {
        model.addAttribute("user", getUser(httpSession));
        return "error/orderFail";
    }

    /**
     * Ticket order page
     * @param model Model
     * @param ticketId Ticket id from DB
     * @param httpSession HTTPSession
     * @return orderedTicket.html - page with purchased ticket
     */
    @GetMapping("orderedTicket")
    public String orderTicket(Model model, @RequestParam("ticketId") int ticketId, HttpSession httpSession) {
        model.addAttribute("movies", sessionService.findAll());
        model.addAttribute("seats", seatGridService.getAllSeats());
        Optional<Ticket> ticket = ticketService.findById(ticketId);
        Ticket ticketObj = new Ticket();
        if (ticket.isPresent()) {
            ticketObj = ticket.get();
        }
        model.addAttribute("tickets", ticketObj);
        model.addAttribute("user", getUser(httpSession));
        return "ticket/orderedTicket";
    }

    /**
     * Ticket creating page
     * @param model Model
     * @param id current session id
     * @param httpSession HTTPSession
     * @return addTicket.html - ticket creating form
     */
    @GetMapping("/formAddTicket/{movieId}")
    public String formAddTicket(Model model, @PathVariable("movieId") int id, HttpSession httpSession) {
        model.addAttribute("seats", seatService.getFreeSeats(id));
        model.addAttribute("movies", sessionService.findAll());
        model.addAttribute("movieId", id);
        model.addAttribute("user", getUser(httpSession));
        return "ticket/addTicket";
    }

    /**
     * Post method for creating a ticket
     * @param model Model
     * @param id current session id
     * @param seatId seat grid id
     * @param httpSession HTTPSession
     * @return order fail page or page with purchased ticket
     */
    @PostMapping("/createTicket")
    public String createTicket(Model model, @RequestParam("movie.sessionId") int id,
                               @RequestParam("seat.seatId") int seatId, HttpSession httpSession) {
        User userAttr = (User) httpSession.getAttribute("user");
        Optional<Session> s = sessionService.findById(id);
        Session session = new Session();
        if (s.isPresent()) {
            session = s.get();
        }
        Optional<User> u = userService.findById(userAttr.getUserId());
        User user = new User();
        if (u.isPresent()) {
            user = u.get();
        }
        Seat seat = seatGridService.findById(seatId);
        if (seat == null) {
            seat = new Seat(-1);
        }
        Ticket ticket = new Ticket(
                0,
                session,
                seat,
                user
                );
        Optional<Ticket> order = ticketService.add(ticket);
        if (order.isEmpty()) {
            return "redirect:/orderFail";
        }
        return "redirect:/orderedTicket?ticketId=" + order.get().getTicketId();
    }

    /**
     * Gives "Guest" name if user unregistered
     * @param httpSession HTTPSession
     * @return user with "Guest" name or user with currrent name
     */
    private User getUser(HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Guest");
        }
        return user;
    }
}
