package ru.yandex.practicum.filmorate.storage;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ElementNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@Component
public class GenresDAO {
    private final Logger log = LoggerFactory.getLogger(GenresDAO.class);

    private final JdbcTemplate jdbcTemplate;

    public GenresDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Collection<Genre> returnAll() {
        String sqlQuery = "select * from Genres";
        return jdbcTemplate.query(sqlQuery, this::mapRowToGenre);
    }

    public Genre returnById(int id) {
        String sqlQuery = "select * from Genres where Genre_Id = ?";
        try {
            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToGenre, id);
        } catch (EmptyResultDataAccessException e) {
            throw new ElementNotFoundException("Жанр с таким id не найден");
        }
    }

    private Genre mapRowToGenre(ResultSet resultSet, int rowNum) throws SQLException {
        return Genre.builder()
                .id(resultSet.getInt("Genre_Id"))
                .name(resultSet.getString("Genre"))
                .build();
    }
}
