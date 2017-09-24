package urchin.domain.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import urchin.domain.exception.UserNotFoundException;
import urchin.domain.model.User;
import urchin.domain.model.UserId;

import java.sql.*;
import java.util.Date;
import java.util.List;

@Repository
public class UserRepository {

    private static final Logger LOG = LoggerFactory.getLogger(UserRepository.class);
    private static final String INSERT_USER = "INSERT INTO user(username, created) VALUES(?,?)";
    private static final String SELECT_USER = "SELECT * from user WHERE id = ?";
    private static final String DELETE_USER = "DELETE FROM user WHERE id = ?";
    private static final String SELECT_USERS = "SELECT * from user";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public UserId saveUser(User user) {
        LOG.info("Saving user {}", user.getUsername());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setTimestamp(2, new Timestamp(new Date().getTime()));
            return preparedStatement;
        }, keyHolder);

        return new UserId(keyHolder.getKey().intValue());
    }

    public User getUser(UserId userId) {
        try {
            return jdbcTemplate.queryForObject(SELECT_USER, new Object[]{userId.getId()}, (resultSet, i) -> userMapper(resultSet));
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException("Invalid userId " + userId);
        }
    }

    public void removeUser(UserId userId) {
        LOG.info("Removing user with id {}", userId);
        jdbcTemplate.update(DELETE_USER, userId.getId());
    }

    public List<User> getUsers() {
        return jdbcTemplate.query(SELECT_USERS, (resultSet, i) -> userMapper(resultSet));
    }

    private User userMapper(ResultSet resultSet) throws SQLException {
        return new User(
                new UserId(resultSet.getInt("id")),
                resultSet.getString("username"),
                resultSet.getTimestamp("created").toLocalDateTime()
        );
    }
}
