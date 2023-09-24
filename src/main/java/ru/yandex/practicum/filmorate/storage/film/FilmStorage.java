package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {
    Collection<Film> returnAll();

    Film create(Film film);

    Film update(Film film);

    Film returnById(long id);

    void addLike(long filmId, long userId);

    void deleteLike(long filmId, long userId);
}
