package urchin.domain.mapper;

import org.springframework.jdbc.core.RowMapper;
import urchin.domain.EncryptedFolder;
import urchin.domain.FolderSettings;

import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FolderSettingsRowMapper implements RowMapper<FolderSettings> {

    @Override
    public FolderSettings mapRow(ResultSet resultSet, int i) throws SQLException {
        FolderSettings folderSettings = new FolderSettings(
                Paths.get(resultSet.getString("folder")),
                new EncryptedFolder(Paths.get(resultSet.getString("encrypted_folder")))
        );
        folderSettings.setId(resultSet.getInt("id"));
        folderSettings.setCreated(resultSet.getTimestamp("created").toLocalDateTime());
        return folderSettings;
    }
}
