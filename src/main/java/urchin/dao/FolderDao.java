package urchin.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import urchin.dao.mapper.FolderSettingsRowMapper;
import urchin.domain.FolderSettings;

import java.util.Date;
import java.util.List;

@Repository
public class FolderDao {

    private static final Logger LOG = LoggerFactory.getLogger(FolderDao.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FolderDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveFolderSettings(FolderSettings folderSettings) {
        LOG.info("Saving new folder settings for folder {}", folderSettings.getFolder());
        jdbcTemplate.update("INSERT INTO folder_settings(encrypted_folder, folder, created) VALUES(?,?,?)",
                folderSettings.getEncryptedFolder().getPath().toAbsolutePath().toString(), folderSettings.getFolder().toAbsolutePath().toString(), new Date());
    }

    public List<FolderSettings> getAllFolderSettings() {
        return jdbcTemplate.query("SELECT * FROM folder_settings", new FolderSettingsRowMapper());
    }

    public void removeFolderSettings(int id) {
        LOG.info("Removing folder settings for id {}", id);
        jdbcTemplate.update("DELETE FROM folder_settings WHERE id = ?", id);
    }
}
