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
import urchin.exception.AdminNotFoundException;
import urchin.model.user.Admin;
import urchin.model.user.AdminId;
import urchin.model.user.ImmutableAdmin;
import urchin.model.user.Username;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Repository
public class AdminRepository {

    private static final String INSERT_ADMIN = "INSERT INTO admin(username, created) VALUES(:username, :created)";
    private static final String SELECT_ADMIN = "SELECT * from admin WHERE id = :id";
    private static final String DELETE_ADMIN = "DELETE FROM admin WHERE id = :id";
    private static final String SELECT_ADMINS = "SELECT * from admin";
    private static final String SELECT_ADMIN_BY_USERNAME = "SELECT * FROM admin WHERE username = :username";
    private static final String USERNAME = "username";
    private static final String CREATED = "created";
    private static final String ID = "id";

    private final Logger log = LoggerFactory.getLogger(AdminRepository.class);
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Autowired
    public AdminRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public AdminId saveAdmin(Username username) {
        log.info("Saving admin {}", username);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue(USERNAME, username.getValue())
                .addValue(CREATED, new Timestamp(new Date().getTime()));

        namedParameterJdbcTemplate.update(INSERT_ADMIN, parameters, keyHolder);

        int adminId = Optional.ofNullable(keyHolder.getKey())
                .map(Number::intValue)
                .orElseThrow(() -> new RuntimeException(format("Failed to save admin %s", username)));

        return AdminId.of(adminId);
    }

    public Admin getAdmin(AdminId adminId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue(ID, adminId.getValue());

        try {
            return namedParameterJdbcTemplate.queryForObject(SELECT_ADMIN, parameters, (resultSet, i) -> adminMapper(resultSet));
        } catch (EmptyResultDataAccessException e) {
            throw new AdminNotFoundException(adminId);
        }
    }

    public void removeAdmin(AdminId adminId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue(ID, adminId.getValue());

        namedParameterJdbcTemplate.update(DELETE_ADMIN, parameters);
    }

    public List<Admin> getAdmins() {
        return namedParameterJdbcTemplate.query(SELECT_ADMINS, (resultSet, i) -> adminMapper(resultSet));
    }

    public Admin getAdminByUsername(Username username) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue(USERNAME, username.getValue());
        try {
            return namedParameterJdbcTemplate.queryForObject(SELECT_ADMIN_BY_USERNAME, parameters, (resultSet, i) -> adminMapper(resultSet));
        } catch (EmptyResultDataAccessException e) {
            throw new AdminNotFoundException(username);
        }
    }

    private Admin adminMapper(ResultSet resultSet) throws SQLException {
        return ImmutableAdmin.builder()
                .adminId(AdminId.of(resultSet.getInt(ID)))
                .username(Username.of(resultSet.getString(USERNAME)))
                .created(resultSet.getTimestamp(CREATED).toLocalDateTime())
                .build();
    }
}
