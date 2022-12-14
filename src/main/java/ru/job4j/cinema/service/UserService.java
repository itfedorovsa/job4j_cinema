package ru.job4j.cinema.service;

import ru.job4j.cinema.model.User;

import java.util.List;
import java.util.Optional;

/**
 * User service interface
 *  @author itfedorovsa (itfedorovsa@gmail.com)
 *  @since 03.11.22
 *  @version 1.0
 */
public interface UserService {
    Optional<User> add(User user);

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndPhone(String email, String phone);

    Optional<User> findById(int id);

    Optional<User> findByPhone(String phone);

    void update(User user);

    List<User> findAll();
}
