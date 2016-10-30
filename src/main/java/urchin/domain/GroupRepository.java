package urchin.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import urchin.domain.model.Group;
import urchin.domain.model.GroupId;

import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class GroupRepository {

    private static final Logger LOG = LoggerFactory.getLogger(GroupRepository.class);
    private static final String INSERT_GROUP = "INSERT INTO user_group(name, created) VALUES(?,?)";
    private static final String SELECT_GROUP = "SELECT * from user_group WHERE id = ?";
    private static final String DELETE_GROUP = "DELETE FROM user_group WHERE id = ?";
    private static final String SELECT_GROUPS = "SELECT * from user_group";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GroupRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public GroupId saveGroup(Group group) {
        LOG.info("Saving group {}", group.getName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_GROUP, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, group.getName());
            preparedStatement.setTimestamp(2, new Timestamp(new Date().getTime()));
            return preparedStatement;
        }, keyHolder);

        return new GroupId(keyHolder.getKey().intValue());
    }

    public Optional<Group> getGroup(GroupId groupId) {
        try {
            return Optional.of(
                    jdbcTemplate.queryForObject(SELECT_GROUP, new Object[]{groupId.getId()}, (resultSet, i) -> groupMapper(resultSet))
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void removeGroup(GroupId groupId) {
        LOG.info("Removing group with id {}", groupId);
        jdbcTemplate.update(DELETE_GROUP, groupId.getId());
    }

    public List<Group> getGroups() {
        return jdbcTemplate.query(SELECT_GROUPS, (resultSet, i) -> groupMapper(resultSet));
    }

    private Group groupMapper(ResultSet resultSet) throws SQLException {
        return new Group(
                new GroupId(resultSet.getInt("id")),
                resultSet.getString("name"),
                resultSet.getTimestamp("created").toLocalDateTime()
        );
    }
}
