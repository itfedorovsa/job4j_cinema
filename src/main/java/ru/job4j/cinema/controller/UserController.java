package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;


/**
 * UserController
 *  @author itfedorovsa (itfedorovsa@gmail.com)
 *  @since 03.11.22
 *  @version 1.0
 */
@ThreadSafe
@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Registration post page
     * @param model Model
     * @param user empty user to fill
     * @return fail or success registration page
     */
    @PostMapping("/registration")
    public String registration(Model model, @ModelAttribute User user) {
        Optional<User> regUser = userService.add(user);
        if (regUser.isEmpty()) {
            model.addAttribute("message", "A user with this email already exists");
            return "redirect:/fail";
        }
        return "redirect:/success";
    }

    /**
     * Sign up form
     * @param model Model
     * @param httpSession HTTPSession
     * @return addUser.html - new user creating form
     */
    @GetMapping("/formAddUser")
    public String addPost(Model model, HttpSession httpSession) {
        model.addAttribute("user", new User(0, "Name", "Email", "Phone"));
        model.addAttribute("user", getUser(httpSession));
        return "user/addUser";
    }

    /**
     * Affirmed registration page
     * @param model Model
     * @param httpSession HTTPSession
     * @return Affirmed registration page
     */
    @GetMapping("/success")
    public String success(Model model, HttpSession httpSession) {
        model.addAttribute("user", new User(0, "Name", "Email", "Phone"));
        model.addAttribute("user", getUser(httpSession));
        return "success";
    }

    /**
     * Declined registration page
     * @param model Model
     * @param httpSession HTTPSession
     * @return Declined registration page
     */
    @GetMapping("/fail")
    public String fail(Model model, HttpSession httpSession) {
        model.addAttribute("user", new User(0, "Name", "Email", "Phone"));
        model.addAttribute("user", getUser(httpSession));
        return "error/fail";
    }

    /**
     * Start registration form
     * @param model Model
     * @param fail fail condition
     * @return login.html - log in form
     */
    @GetMapping("/loginPage")
    public String loginPage(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "login";
    }

    /**
     *  Log in post page
     * @param user current user model
     * @param req request from DB on user presence
     * @return data duplication warning or index page
     */
    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> userDb = userService.findByEmailAndPhone(user.getEmail(), user.getPhone());
        if (userDb.isEmpty()) {
            return "redirect:/loginPage?fail=true";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", userDb.get());
        return "redirect:/index";
    }

    /**
     * Log out page
     * @param httpSession HTTPSession
     * @return log in page
     */
    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/loginPage";
    }

    /**
     * User profile page
     * @param model Model
     * @param httpSession HTTPSession
     * @return profile.html - current user data page
     */
    @GetMapping("/profile")
    public String profile(Model model, HttpSession httpSession) {
        model.addAttribute("user", new User(0, "Name", "Email", "Phone"));
        model.addAttribute("user", getUser(httpSession));
        return "user/profile";
    }

    /**
     * Updating user profile
     * @param model Model
     * @param httpSession HTTPSession
     * @param userId current user id
     * @return updateProfile.html - user updating form
     */
    @GetMapping("/updateProfile/{userId}")
    public String updateProfile(Model model, HttpSession httpSession, @PathVariable("userId") int userId) {
        model.addAttribute("user", userService.findById(userId));
        model.addAttribute("user", getUser(httpSession));
        return "user/updateProfile";
    }

    /**
     * User update post page
     * @param user current user
     * @param httpSession HTTPSession
     * @return log in page to re log in
     */
    @PostMapping("/updateProfile")
    public String updatePost(@ModelAttribute User user, HttpSession httpSession) {
        User u = (User) httpSession.getAttribute("user");
        user.setUserId(u.getUserId());
        userService.update(user);
        httpSession.invalidate();
        return "redirect:/loginPage";
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

