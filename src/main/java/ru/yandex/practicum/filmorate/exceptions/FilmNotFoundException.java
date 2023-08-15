package ru.yandex.practicum.filmorate.exceptions;

public class FilmNotFoundException extends RuntimeException {
    public FilmNotFoundException() {
        super("Такого фильма нет в каталоге");
    }
}
