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
import urchin.exception.FolderNotFoundException;
import urchin.model.folder.*;

import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Repository
public class FolderSettingsRepository {

    private static final String INSERT_FOLDER_SETTINGS = "INSERT INTO folder_settings(encrypted_folder, folder, created, auto_mount) VALUES(:encryptedFolder,:folder,:created,:autoMount)";
    private static final String SELECT_FOLDER_SETTINGS = "SELECT * FROM folder_settings WHERE id = ?";
    private static final String SELECT_FOLDERS_SETTINGS = "SELECT * FROM folder_settings";
    private static final String DELETE_FOLDER_SETTINGS = "DELETE FROM folder_settings WHERE id = ?";

    private final Logger log = LoggerFactory.getLogger(FolderSettingsRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public FolderSettingsRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public FolderSettings getFolderSettings(FolderId folderId) {
        try {
            return jdbcTemplate.queryForObject(SELECT_FOLDER_SETTINGS, new Object[]{folderId.getValue()}, (resultSet, i) -> folderSettingsMapper(resultSet));
        } catch (EmptyResultDataAccessException e) {
            throw new FolderNotFoundException("Invalid folderId " + folderId);
        }
    }

    public List<FolderSettings> getFoldersSettings() {
        return jdbcTemplate.query(SELECT_FOLDERS_SETTINGS, (resultSet, i) -> folderSettingsMapper(resultSet));
    }

    public FolderId saveFolderSettings(EncryptedFolder encryptedFolder, Folder folder) {
        log.info("Saving new folder settings for folder {}", folder);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("encryptedFolder", encryptedFolder.getPath().toAbsolutePath().toString())
                .addValue("folder", folder.toAbsolutePath())
                .addValue("created", new Timestamp(new Date().getTime()))
                .addValue("autoMount", false);

        namedParameterJdbcTemplate.update(INSERT_FOLDER_SETTINGS, parameters, keyHolder);

        return FolderId.of(keyHolder.getKey().intValue());
    }

    public void removeFolderSettings(FolderId folderId) {
        log.info("Removing folder settings for id {}", folderId);
        jdbcTemplate.update(DELETE_FOLDER_SETTINGS, folderId.getValue());
    }

    private FolderSettings folderSettingsMapper(ResultSet resultSet) throws SQLException {
        return ImmutableFolderSettings.builder()
                .folderId(FolderId.of(resultSet.getInt("id")))
                .folder(ImmutableFolder.of(Paths.get(resultSet.getString("folder"))))
                .encryptedFolder(ImmutableEncryptedFolder.of(Paths.get(resultSet.getString("encrypted_folder"))))
                .isAutoMount(resultSet.getBoolean("auto_mount"))
                .created(resultSet.getTimestamp("created").toLocalDateTime())
                .build();
    }
}
