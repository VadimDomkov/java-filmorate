package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private Map<Integer, User> users = new HashMap();
    private int id = 1;

    @GetMapping
    public Collection<User> returnAllUsers() {
        log.info("Запрос GET /users");
        return users.values();
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        log.info("Запрос POST /users");
        user.setId(id);
        if (user.isNameNull()) {
            String login = user.getLogin();
            user.setName(login);
        }
        users.put(id, user);
        id++;
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.info("Запрос PUT /users");
        if (users.containsKey(user.getId())) {
            if (user.isNameNull()) {
                String login = user.getLogin();
                user.setName(login);
            }
            users.put(user.getId(), user);
            return user;
        }
        throw new NoSuchElementException();
    }
}
