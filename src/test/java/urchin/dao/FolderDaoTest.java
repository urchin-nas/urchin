package urchin.dao;

import org.junit.Before;
import org.junit.Test;
import urchin.domain.EncryptedFolder;
import urchin.domain.FolderSettings;
import urchin.testutil.DaoApplication;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class FolderDaoTest extends DaoApplication {

    private FolderDao folderDao;

    @Before
    public void setup() {
        folderDao = new FolderDao(jdbcTemplate);
    }

    @Test
    public void crd() {
        LocalDateTime now = LocalDateTime.now();
        FolderSettings folderSettings = new FolderSettings(Paths.get("/some/path"), new EncryptedFolder(Paths.get("/some/.path")));
        folderDao.saveFolderSettings(folderSettings);
        List<FolderSettings> allFolderSettings = folderDao.getAllFolderSettings();

        assertEquals(1, allFolderSettings.size());
        FolderSettings readFolderSettings = allFolderSettings.get(0);
        assertTrue(readFolderSettings.getId() > 0);
        assertEquals(folderSettings.getFolder(), readFolderSettings.getFolder());
        assertEquals(folderSettings.getEncryptedFolder(), readFolderSettings.getEncryptedFolder());
        assertTrue(now.isBefore(readFolderSettings.getCreated()));

        folderDao.removeFolderSettings(readFolderSettings.getId());

        assertEquals(0, folderDao.getAllFolderSettings().size());
    }

}