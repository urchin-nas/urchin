package urchin.domain.util;

import org.junit.Test;
import urchin.domain.model.EncryptedFolder;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class EncryptedFolderUtilTest {

    @Test
    public void gettingEncryptedFolderFromFolderAndFolderFromEncryptedFolder() {
        Path folder = Paths.get(new File("").getAbsolutePath());
        EncryptedFolder encryptedFolder = EncryptedFolderUtil.getEncryptedFolder(folder);
        Path returnedFolder = EncryptedFolderUtil.getFolder(encryptedFolder);

        assertEquals(folder.toAbsolutePath(), returnedFolder.toAbsolutePath());
        assertNotEquals(folder.toAbsolutePath(), encryptedFolder.getPath().toAbsolutePath());

    }

}