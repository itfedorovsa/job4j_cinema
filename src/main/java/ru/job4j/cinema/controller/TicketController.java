package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@ThreadSafe
@Controller
public class TicketController {
    private final SessionService sessionService;
    private final SeatService seatService;
    private final SeatGridService seatGridService;
    private final TicketService ticketService;
    private final UserService userService;

    public TicketController(SessionService sessionService, SeatService seatService, SeatGridService seatGridService, TicketService ticketService, UserService userService) {
        this.sessionService = sessionService;
        this.seatService = seatService;
        this.seatGridService = seatGridService;
        this.ticketService = ticketService;
        this.userService = userService;
    }

    @GetMapping("/tickets")
    public String tickets(Model model, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        model.addAttribute("movies", sessionService.findAll());
        model.addAttribute("seats", seatService.getAllSeats());
        model.addAttribute("tickets", ticketService.findByUserId(user.getUserId()));
        model.addAttribute("user", getUser(httpSession));
        return "tickets";
    }

    @GetMapping("/orderFail")
    public String orderFail(Model model, HttpSession httpSession) {
        model.addAttribute("user", getUser(httpSession));
        return "orderFail";
    }

    @GetMapping("/orderedTicket")
    public String orderTicket(Model model, @RequestParam("ticketId") int ticketId, HttpSession httpSession) {
        model.addAttribute("movies", sessionService.findAll());
        model.addAttribute("seats", seatService.getAllSeats());
        model.addAttribute("tickets", ticketService.findById(ticketId).get());
        model.addAttribute("user", getUser(httpSession));
        return "orderedTicket";
    }

    @GetMapping("/formAddTicket/{movieId}")
    public String formAddTicket(Model model, @PathVariable("movieId") int id, HttpSession session) {
        model.addAttribute("seats", seatGridService.getFreeSeats(id));
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
            return "redirect:/orderFail";
        }
        return "redirect:/orderedTicket?ticketId=" + order.get().getTicketId();
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
