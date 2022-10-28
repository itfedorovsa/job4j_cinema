package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.SeatService;
import ru.job4j.cinema.service.SessionService;
import ru.job4j.cinema.service.TicketService;
import ru.job4j.cinema.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@ThreadSafe
@Controller
public class TicketController {
    private final SessionService sessionService;
    private final SeatService seatService;
    private final TicketService ticketService;
    private final UserService userService;

    public TicketController(SessionService sessionService, SeatService seatService, TicketService ticketService, UserService userService) {
        this.sessionService = sessionService;
        this.seatService = seatService;
        this.ticketService = ticketService;
        this.userService = userService;
    }

    @GetMapping("/tickets")
    public String tickets(Model model, HttpSession httpSession) {
        model.addAttribute("movies", sessionService.findAll());
        model.addAttribute("seats", seatService.getAllSeats());
        model.addAttribute("tickets", ticketService.findAll());
        model.addAttribute("user", getUser(httpSession));
        return "tickets";
    }

    @GetMapping("/formAddTicket/{movieId}")
    public String formAddTicket(Model model, @PathVariable("movieId") int id, HttpSession session) {
        model.addAttribute("seats", seatService.getAllSeats());
        model.addAttribute("movies", sessionService.findAll());
        model.addAttribute("movieId", id);
        model.addAttribute("user", getUser(session));
        return "addTicket";
    }

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
        Ticket ticket = new Ticket(
                0,
                session,
                seatService.findById(seatId),
                user
                );
        Optional<Ticket> order = ticketService.add(ticket);
        if (order.isEmpty()) {
            return "orderfail";
        }
        return "redirect:/tickets";
    }

    private User getUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Guest");
        }
        return user;
    }
}
