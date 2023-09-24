package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.*;

@Service
public class FilmService {
    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(@Qualifier("filmDbStorage") FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Collection<Film> returnAllEntities() {
        return filmStorage.returnAll();
    }

    public Film createEntity(Film entity) {
        return filmStorage.create(entity);
    }

    public Film updateEntity(Film film) {
        return filmStorage.update(film);
    }

    public Film findById(long id) {
        return filmStorage.returnById(id);
    }

    public void likeMovie(long id, long userId) {
        filmStorage.addLike(id, userId);
    }

    public void unlikeMovie(long id, long userId) {
        filmStorage.deleteLike(id, userId);
    }

    public Collection<Film> findPopular(int count) {
//        Set<Film> films = new TreeSet<>(Film::compareTo);
//        films.addAll(filmStorage.returnAll());
//        if (films.size() > count) {
//            List<Film> filmList = new ArrayList<>(films);
//            List<Film> sublist = filmList.subList(0, count);
//            return filmList.subList(0, count);
//        }
//        return films;
        return filmStorage.getPopularFilms(count);
    }
}
