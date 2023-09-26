package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.Storage;


@Component
public class InMemoryFilmStorage extends Storage<Film> implements FilmStorage {

    @Override
    public Film update(Film film) {
        if (entities.containsKey(film.getId())) {
            return super.update(film);
        }
        throw new FilmNotFoundException();
    }

    @Override
    public Film returnById(long id) {
        if (entities.containsKey(id)) {
            return super.returnById(id);
        }
        throw new FilmNotFoundException();
    }

    @Override
    public void addLike(long filmId, long userId) {

    }

    @Override
    public void deleteLike(long filmId, long userId) {

    }
}
