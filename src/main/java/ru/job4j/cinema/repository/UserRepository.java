package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.User;

import java.util.List;
import java.util.Optional;

/**
 * User persistence interface
 *
 * @author itfedorovsa (itfedorovsa@gmail.com)
 * @version 1.0
 * @since 03.11.22
 */
public interface UserRepository {

    /**
     * Add user
     *
     * @param user {@link ru.job4j.cinema.model.User}
     * @return {@link java.util.Optional<ru.job4j.cinema.model.User>}
     */
    Optional<User> add(User user);

    /**
     * Update user
     *
     * @param user {@link ru.job4j.cinema.model.User}
     */
    void update(User user);

    /**
     * Find user by email
     *
     * @param email User email. Type {@link java.lang.String}
     * @return {@link java.util.Optional<ru.job4j.cinema.model.User>}
     */
    Optional<User> findByEmail(String email);

    /**
     * Find user by id
     *
     * @param id User id
     * @return {@link java.util.Optional<ru.job4j.cinema.model.User>}
     */
    Optional<User> findById(int id);

    /**
     * Find user by phone
     *
     * @param phone User phone. Type {@link java.lang.String}
     * @return {@link java.util.Optional<ru.job4j.cinema.model.User>}
     */
    Optional<User> findByPhone(String phone);

    /**
     * Find User by email and phone
     *
     * @param email User email. Type {@link java.lang.String}
     * @param phone User phone. Type {@link java.lang.String}
     * @return {@link java.util.Optional<ru.job4j.cinema.model.User>}
     */
    Optional<User> findByEmailAndPhone(String email, String phone);

    /**
     * Find all users
     *
     * @return {@link java.util.List<ru.job4j.cinema.model.User>}
     */
    List<User> findAll();
}
