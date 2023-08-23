package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    protected Map<Long, Film> films = new HashMap<>();
    protected long id = 1;

    @Override
    public Collection<Film> returnAllFilms() {
        return films.values();
    }

    @Override
    public Film createFilm(Film film) {
        film.setId(id);
        films.put(id, film);
        id++;
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            return film;
        }
        throw new FilmNotFoundException();
    }

    @Override
    public Film returnFilmById(long id) {
        if (films.containsKey(id)) {
            return films.get(id);
        }
        throw new FilmNotFoundException();
    }
}
