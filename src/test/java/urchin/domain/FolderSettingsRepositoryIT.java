package urchin.domain;

import org.junit.Before;
import org.junit.Test;
import urchin.domain.model.EncryptedFolder;
import urchin.domain.model.FolderSettings;
import urchin.testutil.TestApplication;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


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
        FolderSettings folderSettings = new FolderSettings(Paths.get(workDir + "/some/path"), new EncryptedFolder(Paths.get(workDir + "/some/.path")));
        folderSettings.setAutoMount(true);

        folderSettingsRepository.saveFolderSettings(folderSettings);

        List<FolderSettings> matchedFolderSettings = folderSettingsRepository.getAllFolderSettings().stream()
                .filter(folderSetting -> folderSetting.getFolder().startsWith(workDir))
                .collect(Collectors.toList());
        assertEquals(1, matchedFolderSettings.size());
        FolderSettings readFolderSettings = matchedFolderSettings.get(0);
        assertTrue(readFolderSettings.getId() > 0);
        assertEquals(folderSettings.getFolder(), readFolderSettings.getFolder());
        assertEquals(folderSettings.getEncryptedFolder(), readFolderSettings.getEncryptedFolder());
        assertTrue(now.isBefore(readFolderSettings.getCreated()) || now.isEqual(readFolderSettings.getCreated()));
        assertTrue(readFolderSettings.isAutoMount());

        folderSettingsRepository.removeFolderSettings(readFolderSettings.getId());

        assertEquals(0, folderSettingsRepository.getAllFolderSettings().stream()
                .filter(folderSetting -> folderSetting.getFolder().startsWith(workDir))
                .count());
    }

}