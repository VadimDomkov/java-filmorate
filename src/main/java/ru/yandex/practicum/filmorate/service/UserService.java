package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    public UserService(@Qualifier("userDbStorage") UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public Collection<User> returnAllEntities() {
        return userStorage.returnAll();
    }

    public User findById(long id) {
        return userStorage.returnById(id);
    }

    public User createEntity(User entity) {
        return userStorage.create(entity);
    }

    public User updateEntity(User user) {
        return userStorage.update(user);
    }

    public void addFriend(long id, long friendId) {
        userStorage.addFriend(id, friendId);
    }

    public void deleteFriend(long id, long friendId) {
        userStorage.deleteFriend(id, friendId);
    }

    public List<User> findFriends(long id) {
        return userStorage.getFriends(id).stream().map(i -> userStorage.returnById(i)).collect(Collectors.toList());
    }

    public List<User> findCommonFriends(long id, long otherId) {
        Set<Long> userFriends = userStorage.getFriends(id);
        Set<Long> secondUserFriends = userStorage.getFriends(otherId);
        Set<Long> result = userFriends.stream().filter(i -> secondUserFriends.contains(i)).collect(Collectors.toSet());
        return result.stream().map(i -> userStorage.returnById(i)).collect(Collectors.toList());
    }
}
