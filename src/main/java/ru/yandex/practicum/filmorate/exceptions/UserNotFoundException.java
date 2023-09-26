package ru.yandex.practicum.filmorate.exceptions;

import ru.yandex.practicum.filmorate.model.User;

public class UserNotFoundException extends RuntimeException {
    private User user;

    public UserNotFoundException() {
        super("Пользователя с таким идентификатором не существует");
    }

    public UserNotFoundException(User user) {
        super("Пользователя с таким идентификатором не существует");
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
