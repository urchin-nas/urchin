package urchin.repository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import urchin.exception.FolderNotFoundException;
import urchin.model.folder.*;
import urchin.testutil.TestApplication;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


public class FolderSettingsRepositoryIT extends TestApplication {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private FolderSettingsRepository folderSettingsRepository;

    private Folder folder;
    private EncryptedFolder encryptedFolder;
    private LocalDateTime now;

    @Before
    public void setup() {
        now = LocalDateTime.now();
        String tmpFolderPath = temporaryFolder.getRoot().getAbsolutePath();
        folder = ImmutableFolder.of(Paths.get(tmpFolderPath + "/folder"));
        encryptedFolder = folder.toEncryptedFolder();
        folderSettingsRepository = new FolderSettingsRepository(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Test
    public void crd() {
        folderSettingsRepository.saveFolderSettings(encryptedFolder, folder);

        List<FolderSettings> matchedFolderSettings = folderSettingsRepository.getFoldersSettings().stream()
                .filter(folderSetting -> folderSetting.getFolder().toAbsolutePath().equals(folder.toAbsolutePath()))
                .collect(Collectors.toList());
        assertThat(matchedFolderSettings).hasSize(1);
        FolderSettings readFolderSettings = matchedFolderSettings.get(0);
        assertThat(readFolderSettings.getFolderId().getValue() > 0).isTrue();
        assertThat(readFolderSettings.getFolder()).isEqualTo(folder);
        assertThat(readFolderSettings.getEncryptedFolder()).isEqualTo(encryptedFolder);
        assertThat(now.isBefore(readFolderSettings.getCreated()) || now.isEqual(readFolderSettings.getCreated())).isTrue();
        assertThat(readFolderSettings.isAutoMount()).isFalse();

        folderSettingsRepository.removeFolderSettings(readFolderSettings.getFolderId());

        assertThat(folderSettingsRepository.getFoldersSettings().stream()
                .filter(folderSetting -> folderSetting.getFolder().toAbsolutePath().equals(folder.toAbsolutePath()))
                .count()).isEqualTo(0);
    }

    @Test
    public void getFolders() {
        folderSettingsRepository.saveFolderSettings(encryptedFolder, folder);

        List<FolderSettings> foldersSettings = folderSettingsRepository.getFoldersSettings();

        assertThat(foldersSettings.size() > 0).isTrue();
    }

    @Test
    public void getFolder() {
        FolderId folderId = folderSettingsRepository.saveFolderSettings(encryptedFolder, folder);

        FolderSettings folderSettings = folderSettingsRepository.getFolderSettings(folderId);

        assertThat(folderSettings).isNotNull();
        assertThat(folderSettings.getFolderId()).isEqualTo(folderId);
        assertThat(folderSettings.getEncryptedFolder().getPath().toAbsolutePath()).isEqualTo(encryptedFolder.getPath().toAbsolutePath());
        assertThat(folderSettings.getFolder().toAbsolutePath()).isEqualTo(folder.toAbsolutePath());
        assertThat(now.isBefore(folderSettings.getCreated()) || now.isEqual(folderSettings.getCreated())).isTrue();
        assertThat(folderSettings.isAutoMount()).isFalse();
    }

    @Test(expected = FolderNotFoundException.class)
    public void exceptionIsThrownWhenFolderIsNotFound() {
        folderSettingsRepository.getFolderSettings(FolderId.of(0));
    }

}