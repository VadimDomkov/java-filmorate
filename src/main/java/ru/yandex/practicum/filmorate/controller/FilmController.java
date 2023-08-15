package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping("/films")
public class FilmController extends Controller<Film> {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    public FilmController() {
        this.entities = new HashMap<>();
        this.id = 1;
    }

    @Override
    public Film updateEntity(@Valid @RequestBody Film film) {
        log.info("Запрос PUT /films");
        if (entities.containsKey(film.getId())) {
            return super.updateEntity(film);
        }
        throw new FilmNotFoundException();
    }
}
