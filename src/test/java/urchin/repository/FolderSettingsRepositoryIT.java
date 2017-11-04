package urchin.repository;

import org.junit.Before;
import org.junit.Test;
import urchin.model.folder.EncryptedFolder;
import urchin.model.folder.FolderSettings;
import urchin.model.folder.ImmutableEncryptedFolder;
import urchin.testutil.TestApplication;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;


public class FolderSettingsRepositoryIT extends TestApplication {

    private FolderSettingsRepository folderSettingsRepository;

    @Before
    public void setup() {
        folderSettingsRepository = new FolderSettingsRepository(jdbcTemplate);
    }

    @Test
    public void crd() {
        LocalDateTime now = LocalDateTime.now();
        String workDir = System.getProperty("user.dir");
        Path folder = Paths.get(workDir + "/some/path");
        EncryptedFolder encryptedFolder = ImmutableEncryptedFolder.of(Paths.get(workDir + "/some/.path"));

        folderSettingsRepository.saveFolderSettings(encryptedFolder, folder);

        List<FolderSettings> matchedFolderSettings = folderSettingsRepository.getAllFolderSettings().stream()
                .filter(folderSetting -> folderSetting.getFolder().startsWith(workDir))
                .collect(Collectors.toList());
        assertEquals(1, matchedFolderSettings.size());
        FolderSettings readFolderSettings = matchedFolderSettings.get(0);
        assertTrue(readFolderSettings.getFolderId().getValue() > 0);
        assertEquals(folder, readFolderSettings.getFolder());
        assertEquals(encryptedFolder, readFolderSettings.getEncryptedFolder());
        assertTrue(now.isBefore(readFolderSettings.getCreated()) || now.isEqual(readFolderSettings.getCreated()));
        assertFalse(readFolderSettings.isAutoMount());

        folderSettingsRepository.removeFolderSettings(readFolderSettings.getFolderId());

        assertEquals(0, folderSettingsRepository.getAllFolderSettings().stream()
                .filter(folderSetting -> folderSetting.getFolder().startsWith(workDir))
                .count());
    }

}