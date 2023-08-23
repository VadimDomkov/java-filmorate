package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {
    Collection<Film> returnAllFilms();

    Film createFilm(Film film);

    Film updateFilm(Film film);

    Film returnFilmById(long id);
}
