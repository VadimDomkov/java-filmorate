package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.HashMap;

@Component
public class InMemoryFilmStorage extends Storage<Film> implements FilmStorage {

    public InMemoryFilmStorage() {
        this.entities = new HashMap<>();
        this.id = 1;
    }

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

//    protected Map<Long, Film> films = new HashMap<>();
//    protected long id = 1;
//
//    @Override
//    public Collection<Film> returnAll() {
//        return films.values();
//    }
//
//    @Override
//    public Film create(Film film) {
//        film.setId(id);
//        films.put(id, film);
//        id++;
//        return film;
//    }
//
//    @Override
//    public Film update(Film film) {
//        if (films.containsKey(film.getId())) {
//            films.put(film.getId(), film);
//            return film;
//        }
//        throw new FilmNotFoundException();
//    }
//
//    @Override
//    public Film returnById(long id) {
//        if (films.containsKey(id)) {
//            return films.get(id);
//        }
//        throw new FilmNotFoundException();
//    }
}
