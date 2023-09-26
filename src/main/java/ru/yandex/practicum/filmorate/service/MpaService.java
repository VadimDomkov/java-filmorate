package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.MpaDAO;

import java.util.Collection;

@Service
public class MpaService {
    private final MpaDAO mpaDbStorage;

    public MpaService(MpaDAO mpaDbStorage) {
        this.mpaDbStorage = mpaDbStorage;
    }

    public MPA findById(int id) {
        return mpaDbStorage.returnById(id);
    }

    public Collection<MPA> findAll() {
        return mpaDbStorage.returnAll();
    }
}
