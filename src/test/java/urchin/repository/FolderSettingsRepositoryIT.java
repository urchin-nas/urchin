package urchin.repository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import urchin.model.folder.*;
import urchin.testutil.TestApplication;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;


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
        assertEquals(1, matchedFolderSettings.size());
        FolderSettings readFolderSettings = matchedFolderSettings.get(0);
        assertTrue(readFolderSettings.getFolderId().getValue() > 0);
        assertEquals(folder, readFolderSettings.getFolder());
        assertEquals(encryptedFolder, readFolderSettings.getEncryptedFolder());
        assertTrue(now.isBefore(readFolderSettings.getCreated()) || now.isEqual(readFolderSettings.getCreated()));
        assertFalse(readFolderSettings.isAutoMount());

        folderSettingsRepository.removeFolderSettings(readFolderSettings.getFolderId());

        assertEquals(0, folderSettingsRepository.getFoldersSettings().stream()
                .filter(folderSetting -> folderSetting.getFolder().toAbsolutePath().equals(folder.toAbsolutePath()))
                .count());
    }

    @Test
    public void getFolders() {
        folderSettingsRepository.saveFolderSettings(encryptedFolder, folder);

        List<FolderSettings> foldersSettings = folderSettingsRepository.getFoldersSettings();

        assertTrue(foldersSettings.size() > 0);
    }

    @Test
    public void getFolder() {
        FolderId folderId = folderSettingsRepository.saveFolderSettings(encryptedFolder, folder);

        FolderSettings folderSettings = folderSettingsRepository.getFolderSettings(folderId);

        assertNotNull(folderSettings);
        assertEquals(folderId, folderSettings.getFolderId());
        assertEquals(encryptedFolder.getPath().toAbsolutePath(), folderSettings.getEncryptedFolder().getPath().toAbsolutePath());
        assertEquals(folder.toAbsolutePath(), folderSettings.getFolder().toAbsolutePath());
        assertTrue(now.isBefore(folderSettings.getCreated()) || now.isEqual(folderSettings.getCreated()));
        assertFalse(folderSettings.isAutoMount());

    }

}