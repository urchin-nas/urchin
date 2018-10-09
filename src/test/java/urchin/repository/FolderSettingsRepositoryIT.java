package urchin.repository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.springframework.beans.factory.annotation.Autowired;
import urchin.exception.FolderNotFoundException;
import urchin.model.folder.*;
import urchin.testutil.TestApplication;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


public class FolderSettingsRepositoryIT extends TestApplication {

    private static LocalDateTime NOW = LocalDateTime.now();

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Autowired
    private FolderSettingsRepository folderSettingsRepository;

    private Folder folder;
    private EncryptedFolder encryptedFolder;

    @Before
    public void setup() {
        String tmpFolderPath = temporaryFolder.getRoot().getAbsolutePath();
        folder = ImmutableFolder.of(Paths.get(tmpFolderPath + "/folder"));
        encryptedFolder = folder.toEncryptedFolder();
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
        assertThat(NOW.isBefore(readFolderSettings.getCreated()) || NOW.isEqual(readFolderSettings.getCreated())).isTrue();
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
        assertThat(NOW.isBefore(folderSettings.getCreated()) || NOW.isEqual(folderSettings.getCreated())).isTrue();
        assertThat(folderSettings.isAutoMount()).isFalse();
    }

    @Test(expected = FolderNotFoundException.class)
    public void exceptionIsThrownWhenFolderIsNotFound() {
        folderSettingsRepository.getFolderSettings(FolderId.of(0));
    }

}