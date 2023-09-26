package ru.yandex.practicum.filmorate.storage.user;


import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Set;

public interface UserStorage {
    Collection<User> returnAll();

    User create(User user);

    User update(User user);

    User returnById(long id);

    void addFriend(long id, long friendId);

    void deleteFriend(long id, long friendId);

    Set<Long> getFriends(long id);
}
