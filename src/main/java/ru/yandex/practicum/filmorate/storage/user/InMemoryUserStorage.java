package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.HashMap;

@Component
public class InMemoryUserStorage extends Storage<User> implements UserStorage {

    public InMemoryUserStorage() {
        this.entities = new HashMap<>();
        this.id = 1;
    }

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

//    protected Map<Long, User> users = new HashMap<>();
//    protected long id = 1;
//
//    @Override
//    public Collection<User> returnAll() {
//        return users.values();
//    }
//
//    @Override
//    public User create(User user) {
//        if (user.isNameNull()) {
//            String login = user.getLogin();
//            user.setName(login);
//        }
//        user.setId(id);
//        users.put(id, user);
//        id++;
//        return user;
//    }
//
//    @Override
//    public User update(User user) {
//        if (users.containsKey(user.getId())) {
//            if (user.isNameNull()) {
//                String login = user.getLogin();
//                user.setName(login);
//            }
//            users.put(user.getId(), user);
//            return user;
//        }
//        throw new UserNotFoundException();
//    }
//
//    @Override
//    public User returnById(long id) {
//        if (users.containsKey(id)) {
//            return users.get(id);
//        }
//        throw new UserNotFoundException();
//    }
}
