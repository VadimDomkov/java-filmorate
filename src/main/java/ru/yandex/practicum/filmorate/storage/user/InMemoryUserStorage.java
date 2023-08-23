package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {

    protected Map<Long, User> users = new HashMap<>();
    protected long id = 1;

    @Override
    public Collection<User> returnAllUsers() {
        return users.values();
    }

    @Override
    public User createUser(User user) {
        if (user.isNameNull()) {
            String login = user.getLogin();
            user.setName(login);
        }
        user.setId(id);
        users.put(id, user);
        id++;
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (users.containsKey(user.getId())) {
            if (user.isNameNull()) {
                String login = user.getLogin();
                user.setName(login);
            }
            users.put(user.getId(), user);
            return user;
        }
        throw new UserNotFoundException();
    }

    @Override
    public User returnUserById(long id) {
        if (users.containsKey(id)) {
            return users.get(id);
        }
        throw new UserNotFoundException();
    }
}
