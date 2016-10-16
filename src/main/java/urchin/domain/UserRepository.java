package urchin.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import urchin.domain.model.User;

import java.sql.*;
import java.util.Date;
import java.util.Optional;

@Repository
public class UserRepository {

    private static final Logger LOG = LoggerFactory.getLogger(UserRepository.class);
    private static final String INSERT_USER = "INSERT INTO user(username, created) VALUES(?,?)";
    private static final String SELECT_USER = "SELECT * from user WHERE id = ?";
    private static final String DELETE_USER = "DELETE FROM user WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int saveUser(User user) {
        LOG.info("Saving user {}", user.getUsername());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setTimestamp(2, new Timestamp(new Date().getTime()));
            return preparedStatement;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    public Optional<User> getUser(int id) {
        try {
            return Optional.of(
                    jdbcTemplate.queryForObject(SELECT_USER, new Object[]{id}, (resultSet, i) -> userMapper(resultSet))
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void removeUser(int id) {
        LOG.info("Removing user with id {}", id);
        jdbcTemplate.update(DELETE_USER, id);
    }

    private User userMapper(ResultSet resultSet) throws SQLException {
        User user = new User(
                resultSet.getString("username")
        );
        user.setCreated(resultSet.getTimestamp("created").toLocalDateTime());
        return user;
    }
}
