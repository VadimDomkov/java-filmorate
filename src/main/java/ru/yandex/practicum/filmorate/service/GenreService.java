package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenresDAO;

import java.util.Collection;

@Service
public class GenreService {
    private final GenresDAO genresDbStorage;

    public GenreService(GenresDAO genresDbStorage) {
        this.genresDbStorage = genresDbStorage;
    }

    public Genre findById(int id) {
        return genresDbStorage.returnById(id);
    }

    public Collection<Genre> findAll() {
        return genresDbStorage.returnAll();
    }
}
