package ru.yandex.practicum.filmorate.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ElementNotFoundException;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@Component
public class MpaDAO {
    private final Logger log = LoggerFactory.getLogger(FilmDbStorage.class);

    private final JdbcTemplate jdbcTemplate;

    public MpaDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Collection<MPA> returnAll() {
        String sqlQuery = "select * from MPA";
        try {
            return jdbcTemplate.query(sqlQuery, this::mapRowToMpa);
        } catch (EmptyResultDataAccessException e) {
            throw new ElementNotFoundException("Записей не найдено");
        }
    }

    public MPA returnById(int id) {
        String sqlQuery = "select * from MPA where MPA_Id = ?";
        try {
            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToMpa, id);
        } catch (EmptyResultDataAccessException e) {
            throw new ElementNotFoundException("Рейтинг с id " + id + " не найден");
        }
    }

    private MPA mapRowToMpa(ResultSet resultSet, int rowNum) throws SQLException {
        return MPA.builder()
                .id(resultSet.getInt("MPA_Id"))
                .name(resultSet.getString("MPA_Name"))
                .build();
    }
}
