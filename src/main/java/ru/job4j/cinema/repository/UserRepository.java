package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.User;

import java.util.List;
import java.util.Optional;

/**
 *  User persistence interface
 *  @author itfedorovsa (itfedorovsa@gmail.com)
 *  @since 03.11.22
 *  @version 1.0
 */
public interface UserRepository {

    Optional<User> add(User user);

    void update(User user);

    Optional<User> findByEmail(String email);

    Optional<User> findById(int id);

    Optional<User> findByPhone(String phone);

    Optional<User> findByEmailAndPhone(String email, String phone);

    List<User> findAll();
}
