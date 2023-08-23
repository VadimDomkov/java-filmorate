package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.*;

@Service
public class FilmService {
    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Collection<Film> returnAllEntities() {
        return filmStorage.returnAllFilms();
    }

    public Film createEntity(Film entity) {
        return filmStorage.createFilm(entity);
    }

    public Film updateEntity(Film film) {
        return filmStorage.updateFilm(film);
    }

    public Film findFilmById(long id) {
        return filmStorage.returnFilmById(id);
    }

    public void likeMovie(long id, long userId) {
        Film film = filmStorage.returnFilmById(id);
        film.addLike(userId);
    }

    public void unlikeMovie(long id, long userId) {
        Film film = filmStorage.returnFilmById(id);
        film.deleteLike(userId);
    }

    public Collection<Film> findPopular(int count) {
        Set<Film> films = new TreeSet<>(Film::compareTo);
        films.addAll(filmStorage.returnAllFilms());
        if (films.size() > count) {
            List<Film> filmList = new ArrayList<>(films);
            List<Film> sublist = filmList.subList(0, count);
            return filmList.subList(0, count);
        }
        return films;
    }
}
