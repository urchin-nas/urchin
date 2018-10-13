package urchin.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserRepository {

    private static final String INSERT_USER = "INSERT INTO user(username, created) VALUES(:username, :created)";
    private static final String SELECT_USER = "SELECT * from user WHERE id = :userId";
    private static final String DELETE_USER = "DELETE FROM user WHERE id = :userId";
    private static final String SELECT_USERS = "SELECT * from user";
    private static final String SELECT_USERS_BY_USERNAME = "SELECT * FROM user WHERE username IN (:usernames)";

    private final Logger log = LoggerFactory.getLogger(UserRepository.class);
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public UserRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public UserId saveUser(Username username) {
        log.info("Saving user {}", username);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("username", username.getValue())
                .addValue("created", new Timestamp(new Date().getTime()));

        namedParameterJdbcTemplate.update(INSERT_USER, params, keyHolder);

        int userId = Optional.ofNullable(keyHolder.getKey())
                .map(Number::intValue)
                .orElseThrow(() -> new RuntimeException(String.format("Failed to save user %s", username)));

        return UserId.of(userId);
    }

    public User getUser(UserId userId) {
        try {
            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("userId", userId.getValue());
            return namedParameterJdbcTemplate.queryForObject(SELECT_USER, params, (resultSet, i) -> userMapper(resultSet));
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException(userId);
        }
    }

    public void removeUser(UserId userId) {
        log.info("Removing user with id {}", userId);
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", userId.getValue());
        namedParameterJdbcTemplate.update(DELETE_USER, params);
    }

    public List<User> getUsers() {
        return namedParameterJdbcTemplate.query(SELECT_USERS, (resultSet, i) -> userMapper(resultSet));
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
