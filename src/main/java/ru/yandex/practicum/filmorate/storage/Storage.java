package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Storage<T extends Entity> {
    protected Map<Long, T> entities = new HashMap<>() ;
    protected long id = 1;

    public Collection<T> returnAll() {
        return entities.values();
    }

    public T create(T entity) {
        entity.setId(id);
        entities.put(id, entity);
        id++;
        return entity;
    }

    public T update(T entity) {
        entities.put(entity.getId(), entity);
        return entity;
    }

    public T returnById(long id) {
        return entities.get(id);
    }
}
