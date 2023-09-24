package ru.yandex.practicum.filmorate.exceptions;

import ru.yandex.practicum.filmorate.model.Film;

public class FilmNotFoundException extends RuntimeException {
    private Film film;
    public FilmNotFoundException() {
        super("Такого фильма нет в каталоге");
    }

    public FilmNotFoundException(Film film) {
        this.film = film;
    }

    public Film getFilm() {
        return film;
    }
}
