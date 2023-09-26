package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.Set;

@Component
public class InMemoryUserStorage extends Storage<User> implements UserStorage {

    @Override
    public User create(User user) {
        if (user.isNameNull()) {
            String login = user.getLogin();
            user.setName(login);
        }
        return super.create(user);
    }

    @Override
    public User update(User user) {
        if (entities.containsKey(user.getId())) {
            if (user.isNameNull()) {
                String login = user.getLogin();
                user.setName(login);
            }
            return super.update(user);
        }
        throw new UserNotFoundException();
    }

    @Override
    public User returnById(long id) {
        if (entities.containsKey(id)) {
            return super.returnById(id);
        }
        throw new UserNotFoundException();
    }

    @Override
    public void addFriend(long id, long friendId) {
        return;
    }

    @Override
    public void deleteFriend(long id, long friendId) {

    }

    @Override
    public Set<Long> getFriends(long id) {
        return null;
    }
}
