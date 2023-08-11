package ru.yandex.practicum.filmorate.exceptions;

public class NoSuchFilmException extends RuntimeException {
    public NoSuchFilmException() {
        super("Такого фильма нет в каталоге");
    }
}
