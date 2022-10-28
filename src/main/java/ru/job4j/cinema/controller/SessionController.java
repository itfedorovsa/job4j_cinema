package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.SeatService;
import ru.job4j.cinema.service.SessionService;

import javax.servlet.http.HttpSession;

@ThreadSafe
@Controller
public class SessionController {
    private final SessionService sessionService;
    private final SeatService seatService;

    public SessionController(SessionService sessionService, SeatService seatService) {
        this.sessionService = sessionService;
        this.seatService = seatService;
    }

    @GetMapping("/sessions")
    public String addSession(Model model, HttpSession httpSession) {
        model.addAttribute("seats", seatService.getAllSeats());
        model.addAttribute("movies", sessionService.findAll());
        model.addAttribute("user", getUser(httpSession));
        return "sessions";
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
