package ru.yandex.practicum.filmorate.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("Пользователя с таким идентификатором не существует");
    }
}
