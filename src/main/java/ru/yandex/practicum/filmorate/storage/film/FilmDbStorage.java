package ru.yandex.practicum.filmorate.storage.film;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ElementNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.EntryAlreadyExistsException;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class FilmDbStorage implements FilmStorage{
    private final Logger log = LoggerFactory.getLogger(FilmDbStorage.class);

    private final JdbcTemplate jdbcTemplate;

    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<Film> returnAll() {
        String sqlQuery = "select * from Films";

        return jdbcTemplate.query(sqlQuery, this::mapRowToFilm);
    }

    @Override
    public Film create(Film film) {
        String sqlQuery = "insert into films (Name, Description, Release_date, Duration, MPA_Id) values (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sqlQuery, new String[]{"Film_id"});
            statement.setString(1, film.getName());
            statement.setString(2, film.getDescription());
            statement.setString(3, film.getReleaseDate().toString());
            statement.setInt(4, film.getDuration());
            statement.setInt(5, film.getMpa().getId());
            return statement;
        }, keyHolder);
        long filmId = keyHolder.getKey().longValue();
        film.setId(filmId);
        if (film.getGenres()!=null) {
            log.info("Genres: " + film.getGenres().toString());
            updateGenres(filmId, film.getGenres());
        }
        log.info("");
        return film;
    }

    private void updateGenres(long filmId, Set<Genre> genres) {
        String deleteQuery = "delete from Genres_To_Films where Film_Id = ?";
        String updateQuery = "insert into Genres_To_Films (Film_Id, Genre_Id) values (?, ?)";
        jdbcTemplate.update(deleteQuery, filmId);
        genres.stream().forEach(i -> jdbcTemplate.update(updateQuery, filmId, i.getId()));
    }

    @Override
    public Film update(Film film) {
        String sqlQuery = "update films set name = ?, description = ?, release_date = ?, duration = ?, MPA_Id = ?" +
                "where film_id = ?";
        try {
            String sqlSelect = "select * from Films where film_id = ?";
            jdbcTemplate.queryForObject(sqlSelect, this::mapRowToFilm, film.getId());
            jdbcTemplate.update(sqlQuery, film.getName(), film.getDescription(), film.getReleaseDate().toString(), film.getDuration(), film.getMpa().getId(), film.getId());
            if (film.getGenres()!=null) {
                updateGenres(film.getId(), film.getGenres());
            }
            film.setGenres(getGenres(film.getId()));
            return film;
        } catch (EmptyResultDataAccessException e) {
            throw new FilmNotFoundException(film);
        }

    }

    @Override
    public Film returnById(long id) {
        String sqlQuery = "select * from Films where film_id = ?";
        try {
            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToFilm, id);
        } catch (EmptyResultDataAccessException e) {
            throw new ElementNotFoundException("Фильм с id " + id + " не найден");
        }

    }
    
    private Film mapRowToFilm(ResultSet resultSet, int rowNum) throws SQLException {
        int mpaId = resultSet.getInt("MPA_Id");
        Film film = Film.builder()
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .duration(resultSet.getInt("duration"))
                .releaseDate(resultSet.getDate("release_date").toLocalDate())
                .mpa(MPA.builder().id(mpaId).name(getMpaName(mpaId)).build())
                .build();
        long filmId = resultSet.getLong("film_id");
        film.setId(filmId);
        film.setLikes(getLikes(filmId));
        film.setGenres(getGenres(filmId));
        return film;
    }

    private Set<Genre> getGenres(long filmId) {
        String sqlQuery = "select Genre_Id from Genres_To_Films where Film_Id = ?";
        Set<Integer> ids = new HashSet<Integer>(jdbcTemplate.query(sqlQuery, (resultSet, rowNum) -> resultSet.getInt("Genre_Id"), filmId));
        return ids.stream().map(i -> Genre.builder().id(i).name(getGenreName(i)).build()).collect(Collectors.toSet());
    }

    public void addLike(long filmId, long userId) {
        String sqlQuery = "insert into Likes (Film_Id, User_Id) values (?, ?)";
        try {
            jdbcTemplate.update(sqlQuery, filmId, userId);
        } catch (DataIntegrityViolationException e) {
            throw new EntryAlreadyExistsException("Запись уже существует");
        }
    }

    public Set<Long> getLikes(long id) {
        String sqlQuery = "select User_Id from Likes where Film_Id = ?";
        return new HashSet<>(jdbcTemplate.query(sqlQuery, (resultSet, rowNum) -> resultSet.getLong("User_Id"), id));
    }

    public void deleteLike(long filmId, long userId) {
        String sqlQuery = "delete from Likes where Film_Id = ? and User_Id = ?";
        try {
            String sqlSelect = "select * from Likes where film_id = ? and User_Id = ?";
            jdbcTemplate.queryForObject(sqlSelect, ((rs, rowNum) -> rs.getInt("user_id")), filmId, userId);
            jdbcTemplate.update(sqlQuery, filmId, userId);
        } catch (EmptyResultDataAccessException e) {
            throw new ElementNotFoundException("Запись не найдена");
        }

    }

    public String getMpaName(int id) {
        String sqlQuery = "select * from MPA where MPA_Id = ?";
        try {
            return jdbcTemplate.query(sqlQuery, (resultSet, rowNum) -> resultSet.getString("MPA_Name"), id).get(0);
        } catch (EmptyResultDataAccessException e) {
            throw new ElementNotFoundException("MPA с идентификатором" + id + "не найден");
        }
    }

    public String getGenreName(int id) {
        String sqlQuery = "select * from Genres where Genre_Id = ?";
        try {
            return jdbcTemplate.query(sqlQuery, (resultSet, rowNum) -> resultSet.getString("Genre"), id).get(0);
        } catch (EmptyResultDataAccessException e) {
            throw new ElementNotFoundException("Жанр с идентификатором" + id + "не найден");
        }
    }

    public Set<Film> getPopularFilms(int count) {
        String sqlQuery = "select Film_Id, COUNT (User_Id) from Likes GROUP BY FILM_Id ORDER BY COUNT (User_Id) desc LIMIT ?";
        Set<Long> ids = new HashSet<>(jdbcTemplate.query(sqlQuery, (resultSet, rowNum) -> resultSet.getLong("Film_Id"), count));
        return ids.stream().map(this::returnById).collect(Collectors.toSet());
    }

}
