package urchin.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import urchin.exception.UserNotFoundException;
import urchin.model.user.ImmutableUser;
import urchin.model.user.User;
import urchin.model.user.UserId;
import urchin.model.user.Username;

import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserRepository {

    private static final String INSERT_USER = "INSERT INTO user(username, created) VALUES(?,?)";
    private static final String SELECT_USER = "SELECT * from user WHERE id = ?";
    private static final String DELETE_USER = "DELETE FROM user WHERE id = ?";
    private static final String SELECT_USERS = "SELECT * from user";
    private static final String SELECT_USERS_BY_USERNAME = "SELECT * FROM user WHERE username IN (:usernames)";

    private final Logger log = LoggerFactory.getLogger(UserRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public UserId saveUser(Username username) {
        log.info("Saving user {}", username);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, username.getValue());
            preparedStatement.setTimestamp(2, new Timestamp(new Date().getTime()));
            return preparedStatement;
        }, keyHolder);

        return UserId.of(keyHolder.getKey().intValue());
    }

    public User getUser(UserId userId) {
        try {
            return jdbcTemplate.queryForObject(SELECT_USER, new Object[]{userId.getValue()}, (resultSet, i) -> userMapper(resultSet));
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException(userId);
        }
    }

    public void removeUser(UserId userId) {
        log.info("Removing user with id {}", userId);
        jdbcTemplate.update(DELETE_USER, userId.getValue());
    }

    public List<User> getUsers() {
        return jdbcTemplate.query(SELECT_USERS, (resultSet, i) -> userMapper(resultSet));
    }

    public List<User> getUsersByUsername(List<Username> usernames) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("usernames", getUsernames(usernames));

        return namedParameterJdbcTemplate.query(SELECT_USERS_BY_USERNAME, parameters, (resultSet, i) -> userMapper(resultSet));
    }

    private List<String> getUsernames(List<Username> usernames) {
        return usernames.stream()
                .map(Username::getValue)
                .collect(Collectors.toList());
    }

    private User userMapper(ResultSet resultSet) throws SQLException {
        return ImmutableUser.builder()
                .userId(UserId.of(resultSet.getInt("id")))
                .username(Username.of(resultSet.getString("username")))
                .created(resultSet.getTimestamp("created").toLocalDateTime())
                .build();
    }
}
