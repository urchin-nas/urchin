package urchin.domain.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import urchin.domain.model.EncryptedFolder;
import urchin.domain.model.FolderSettings;

import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class FolderSettingsRepository {

    private static final Logger LOG = LoggerFactory.getLogger(FolderSettingsRepository.class);
    private static final String INSERT_FOLDER_SETTINGS = "INSERT INTO folder_settings(encrypted_folder, folder, created, auto_mount) VALUES(?,?,?,?)";
    private static final String SELECT_FOLDER_SETTINGS = "SELECT * FROM folder_settings";
    private static final String DELETE_FOLDER_SETTINGS = "DELETE FROM folder_settings WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FolderSettingsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveFolderSettings(FolderSettings folderSettings) {
        LOG.info("Saving new folder settings for folder {}", folderSettings.getFolder());
        jdbcTemplate.update(
                INSERT_FOLDER_SETTINGS,
                folderSettings.getEncryptedFolder().getPath().toAbsolutePath().toString(),
                folderSettings.getFolder().toAbsolutePath().toString(),
                new Date(),
                folderSettings.isAutoMount()
        );
    }

    public List<FolderSettings> getAllFolderSettings() {
        return jdbcTemplate.query(SELECT_FOLDER_SETTINGS, (resultSet, i) -> folderSettingsMapper(resultSet));
    }

    public void removeFolderSettings(int id) {
        LOG.info("Removing folder settings for id {}", id);
        jdbcTemplate.update(DELETE_FOLDER_SETTINGS, id);
    }

    private FolderSettings folderSettingsMapper(ResultSet resultSet) throws SQLException {
        FolderSettings folderSettings = new FolderSettings(
                Paths.get(resultSet.getString("folder")),
                new EncryptedFolder(Paths.get(resultSet.getString("encrypted_folder")))
        );
        folderSettings.setId(resultSet.getInt("id"));
        folderSettings.setCreated(resultSet.getTimestamp("created").toLocalDateTime());
        folderSettings.setAutoMount(resultSet.getBoolean("auto_mount"));
        return folderSettings;
    }
}
