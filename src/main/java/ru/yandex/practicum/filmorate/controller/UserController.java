package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/users")
public class UserController extends Controller<User> {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    public UserController() {
        this.entities = new HashMap();
        this.id = 1;
    }

    @Override
    public User createEntity(@Valid @RequestBody User user) {
        if (user.isNameNull()) {
            String login = user.getLogin();
            user.setName(login);
        }
        return super.createEntity(user);
    }

    @Override
    public User updateEntity(@Valid @RequestBody User user) {
        log.info("Запрос PUT /users");
        if (entities.containsKey(user.getId())) {
            if (user.isNameNull()) {
                String login = user.getLogin();
                user.setName(login);
            }
            return super.updateEntity(user);
        }
        throw new NoSuchElementException();
    }
}
