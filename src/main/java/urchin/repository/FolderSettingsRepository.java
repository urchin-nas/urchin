package urchin.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import urchin.model.folder.EncryptedFolder;
import urchin.model.folder.FolderSettings;
import urchin.model.folder.ImmutableEncryptedFolder;
import urchin.model.folder.ImmutableFolderSettings;

import java.nio.file.Path;
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

    public void saveFolderSettings(EncryptedFolder encryptedFolder, Path folder) {
        LOG.info("Saving new folder settings for folder {}", folder);
        jdbcTemplate.update(
                INSERT_FOLDER_SETTINGS,
                encryptedFolder.getPath().toAbsolutePath().toString(),
                folder.toAbsolutePath().toString(),
                new Date(),
                false
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
        return ImmutableFolderSettings.builder()
                .id(resultSet.getInt("id"))
                .folder(Paths.get(resultSet.getString("folder")))
                .encryptedFolder(ImmutableEncryptedFolder.of(Paths.get(resultSet.getString("encrypted_folder"))))
                .isAutoMount(resultSet.getBoolean("auto_mount"))
                .created(resultSet.getTimestamp("created").toLocalDateTime())
                .build();
    }
}
