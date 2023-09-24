package ru.yandex.practicum.filmorate.storage.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ElementNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
public class UserDbStorage implements UserStorage{
    private final Logger log = LoggerFactory.getLogger(FilmDbStorage.class);

    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<User> returnAll() {
        String sqlQuery = "select * from Users";
        try {
            return jdbcTemplate.query(sqlQuery, this::mapRowToUser);
        } catch (EmptyResultDataAccessException e) {
            throw new ElementNotFoundException("Записей не найдено");
        }
    }

    @Override
    public User create(User user) {
        if (user.isNameNull()) {
            String login = user.getLogin();
            user.setName(login);
        }

        String sqlQuery = "insert into users (Email, Login, Username, Birthdate) values (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sqlQuery, new String[]{"User_id"});
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getName());
            statement.setString(4, user.getBirthday().toString());
            return statement;
        }, keyHolder);

        long userId = keyHolder.getKey().longValue();
        user.setId(userId);
        log.info("User created");
        return user;
    }

    @Override
    public User update(User user) {
        if (user.isNameNull()) {
            String login = user.getLogin();
            user.setName(login);
        }
        String sqlQuery = "update users set email = ?, login = ?, username = ?, birthdate = ? " +
                "where user_id = ?";
        try{
            String sqlSelect = "select * from Users where user_id = ?";
            jdbcTemplate.queryForObject(sqlSelect, this::mapRowToUser, user.getId());
            jdbcTemplate.update(sqlQuery, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(), user.getId());
            return user;
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException(user);
        }
    }

    @Override
    public User returnById(long id) {
        String sqlQuery = "select * from Users where user_id = ?";
        try {
            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToUser, id);
        } catch (EmptyResultDataAccessException e) {
            throw new ElementNotFoundException("Пользователь с Id " + id + " не найден");
        }
    }

    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        User user = User.builder()
                .name(resultSet.getString("Username"))
                .email(resultSet.getString("email"))
                .login(resultSet.getString("login"))
                .birthday(resultSet.getDate("birthdate").toLocalDate())
                .build();
        user.setId(resultSet.getLong("user_id"));
        user.setFriends(getFriends(resultSet.getLong("user_id")));
        return user;
    }

    public Set<Long> getFriends(long id) {
        String sqlQuery = "select Friend_Id from Friends where User_Id = ?";
        return new HashSet<>(jdbcTemplate.query(sqlQuery, (resultSet, rowNum) -> resultSet.getLong("Friend_Id"), id));
    }

    public void addFriend(long userId, long friendId) {
        String sqlQuery = "insert into friends (User_Id, Friend_Id) values(?, ?)";
        try {
            jdbcTemplate.update(sqlQuery, userId, friendId);
        } catch (DataIntegrityViolationException e) {
            throw new UserNotFoundException(User.builder().build());
        }
    }

    public void deleteFriend(long userId, long friendId) {
        String sqlQuery = "delete from friends where User_Id = ? and Friend_Id = ?";
        jdbcTemplate.update(sqlQuery, userId, friendId);
    }
}
