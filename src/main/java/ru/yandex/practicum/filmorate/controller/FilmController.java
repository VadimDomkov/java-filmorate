package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/films")
public class FilmController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public Collection<Film> returnAllEntities() {
        return filmService.returnAllEntities();
    }

    @GetMapping("/{id}")
    public Film findFilmById(@PathVariable long id) {
        log.info("Запрос GET /films/{" + id + "}");
        return filmService.findById(id);
    }

    @PostMapping
    public Film createEntity(@Valid @RequestBody Film entity) {
        return filmService.createEntity(entity);
    }

    @PutMapping
    public Film updateEntity(@Valid @RequestBody Film film) {
        log.info("Запрос PUT /films");
        return filmService.updateEntity(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public void likeMovie(@PathVariable long id, @PathVariable long userId) {
        log.info("Запрос PUT /films/{id}/like/{userId}");
        filmService.likeMovie(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void unlikeMovie(@PathVariable long id, @PathVariable long userId) {
        log.info("Запрос DELETE /films/{" + id + "}/like/{" + userId + "}");
        filmService.unlikeMovie(id, userId);
    }

    @GetMapping("/popular")
    public Collection<Film> findPopular(@RequestParam(defaultValue = "10") int count) {
        int filmCount = count;
        log.info("Запрос GET /films/popular?count={" + filmCount + "}");
        return filmService.findPopular(count);
    }
}
