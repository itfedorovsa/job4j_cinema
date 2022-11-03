package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.SeatGridService;
import ru.job4j.cinema.service.SessionService;

import javax.servlet.http.HttpSession;

/**
 * SessionController
 * @author itfedorovsa (itfedorovsa@gmail.com)
 * @since 03.11.22
 * @version 1.0
 */
@ThreadSafe
@Controller
public class SessionController {
    private final SessionService sessionService;
    private final SeatGridService seatGridService;

    public SessionController(SessionService sessionService, SeatGridService seatGridService) {
        this.sessionService = sessionService;
        this.seatGridService = seatGridService;
    }

    /**
     * Sessions page
     * @param model Model
     * @param httpSession HTTPSession
     * @return sessions.html - all movies page
     */
    @GetMapping("/sessions")
    public String addSession(Model model, HttpSession httpSession) {
        model.addAttribute("seats", seatGridService.getAllSeats());
        model.addAttribute("movies", sessionService.findAll());
        model.addAttribute("user", getUser(httpSession));
        return "sessions";
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
