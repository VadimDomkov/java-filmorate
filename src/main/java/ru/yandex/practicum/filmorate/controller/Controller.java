package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Entity;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Map;

@RestController
public abstract class Controller<T extends Entity> {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    protected Map<Integer, T> entities;
    protected int id;

    @GetMapping
    public Collection<T> returnAllEntities() {
        log.info("Запрос GET");
        return entities.values();
    }

    @PostMapping
    public T createEntity(@Valid @RequestBody T entity) {
        log.info("Запрос POST");
        entity.setId(id);
        entities.put(id, entity);
        id++;
        return entity;
    }

    @PutMapping
    public T updateEntity(@Valid @RequestBody T entity) {
        entities.put(entity.getId(), entity);
        return entity;
    }

}
