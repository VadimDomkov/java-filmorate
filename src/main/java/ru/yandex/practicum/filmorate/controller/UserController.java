package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Collection<User> returnAllEntities() {
        log.info("Запрос GET /users");
        return userService.returnAllEntities();
    }

    @GetMapping("/{id}")
    public User findUserById(@PathVariable long id) {
        log.info("Запрос GET /users/{" + id + "}");
        return userService.findById(id);
    }

    @PostMapping
    public User createEntity(@Valid @RequestBody User user) {
        log.info("Запрос Post /users");
        return userService.createEntity(user);
    }

    @PutMapping
    public User updateEntity(@Valid @RequestBody User user) {
        log.info("Запрос PUT /users");
        return userService.updateEntity(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable long id, @PathVariable long friendId) {
        log.info("Запрос PUT /users/{" + id + "}/friends/{" + friendId + "}");
        userService.addFriend(id, friendId);

    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable long id, @PathVariable long friendId) {
        log.info("Запрос DELETE /users/{" + id + "}/friends/{" + friendId + "}");
        userService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> findFriends(@PathVariable long id) {
        log.info("Запрос GET /users/{" + id + "}/friends");
        return userService.findFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> findCommonFriends(@PathVariable long id, @PathVariable long otherId) {
        log.info("Запрос GET /users/{" + id + "}/friends/common/{" + otherId + "}");
        return userService.findCommonFriends(id, otherId);
    }
}
