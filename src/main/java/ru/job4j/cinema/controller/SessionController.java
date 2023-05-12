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
 *
 * @author itfedorovsa (itfedorovsa@gmail.com)
 * @version 1.0
 * @since 03.11.22
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
     *
     * @param model       Model
     * @param httpSession HTTPSession
     * @return sessions.html - all movies page
     */
    @GetMapping("/sessions")
    public String addSession(Model model, HttpSession httpSession) {
        model.addAttribute("seats", seatGridService.getAllSeats());
        model.addAttribute("movies", sessionService.findAll());
        model.addAttribute("user", getUser(httpSession));
        return "session/sessions";
    }

    /**
     * Create a user with name "Guest" if user is missing
     *
     * @param httpSession HTTPSession
     * @return new User with "Guest" name or current User
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
