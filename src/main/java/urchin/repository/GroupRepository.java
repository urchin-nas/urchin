package urchin.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import urchin.exception.GroupNotFoundException;
import urchin.model.Group;
import urchin.model.GroupId;
import urchin.model.ImmutableGroup;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Repository
public class GroupRepository {

    private static final Logger LOG = LoggerFactory.getLogger(GroupRepository.class);
    private static final String INSERT_GROUP = "INSERT INTO user_group(name, created) VALUES(:name, :created)";
    private static final String SELECT_GROUP = "SELECT * from user_group WHERE id = :groupId";
    private static final String DELETE_GROUP = "DELETE FROM user_group WHERE id = :groupId";
    private static final String SELECT_GROUPS = "SELECT * from user_group";
    private static final String SELECT_GROUPS_BY_NAME = "SELECT * FROM user_group WHERE name IN (:names)";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public GroupRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public GroupId saveGroup(String groupName) {
        LOG.info("Saving group {}", groupName);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("name", groupName)
                .addValue("created", new Timestamp(new Date().getTime()));

        namedParameterJdbcTemplate.update(INSERT_GROUP, parameters, keyHolder);
        return GroupId.of(keyHolder.getKey().intValue());
    }

    public Group getGroup(GroupId groupId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("groupId", groupId.getValue());
        try {
            return namedParameterJdbcTemplate.queryForObject(SELECT_GROUP, parameters, (resultSet, i) -> groupMapper(resultSet));
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new GroupNotFoundException("Invalid groupId " + groupId);
        }
    }

    public void removeGroup(GroupId groupId) {
        LOG.info("Removing group with id {}", groupId);
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("groupId", groupId.getValue());

        namedParameterJdbcTemplate.update(DELETE_GROUP, parameters);
    }

    public List<Group> getGroups() {
        return namedParameterJdbcTemplate.query(SELECT_GROUPS, (resultSet, i) -> groupMapper(resultSet));
    }

    public List<Group> getGroupsByName(List<String> groupNames) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("names", groupNames);

        return namedParameterJdbcTemplate.query(SELECT_GROUPS_BY_NAME, parameters, (resultSet, i) -> groupMapper(resultSet));
    }

    private Group groupMapper(ResultSet resultSet) throws SQLException {
        return ImmutableGroup.builder()
                .groupId(GroupId.of(resultSet.getInt("id")))
                .name(resultSet.getString("name"))
                .created(resultSet.getTimestamp("created").toLocalDateTime())
                .build();
    }
}
