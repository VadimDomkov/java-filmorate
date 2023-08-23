package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public Collection<User> returnAllEntities() {
        return userStorage.returnAllUsers();
    }

    public User findUserById(long id) {
        return userStorage.returnUserById(id);
    }

    public User createEntity(User entity) {
        return userStorage.createUser(entity);
    }

    public User updateEntity(User user) {
        return userStorage.updateUser(user);
    }

    public void addFriend(long id, long friendId) {
        User user = userStorage.returnUserById(id);
        User friend = userStorage.returnUserById(friendId);
        user.addFriend(friendId);
        friend.addFriend(id);
    }

    public void deleteFriend(long id, long friendId) {
        User user = userStorage.returnUserById(id);
        User friend = userStorage.returnUserById(friendId);
        user.removeFriend(friendId);
        friend.removeFriend(id);
    }

    public List<User> findFriends(long id) {
        User user = userStorage.returnUserById(id);
        return user.getFriends().stream().map(i -> userStorage.returnUserById(i)).collect(Collectors.toList());
    }

    public List<User> findCommonFriends(long id, long otherId) {
        Set<Long> userFriends = userStorage.returnUserById(id).getFriends();
        Set<Long> secondUserFriends = userStorage.returnUserById(otherId).getFriends();
        Set<Long> result = userFriends.stream().filter(i -> secondUserFriends.contains(i)).collect(Collectors.toSet());
        return result.stream().map(i -> userStorage.returnUserById(i)).collect(Collectors.toList());
    }
}
