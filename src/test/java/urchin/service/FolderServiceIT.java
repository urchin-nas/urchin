package urchin.service;

import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import urchin.domain.FolderSettingsRepository;
import urchin.domain.model.FolderSettings;
import urchin.domain.model.Passphrase;
import urchin.testutil.TemporaryFolderUnmount;
import urchin.testutil.TestApplication;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static java.nio.file.Files.exists;
import static junit.framework.TestCase.*;
import static urchin.domain.util.EncryptedFolderUtil.getEncryptedFolder;
import static urchin.testutil.OsAssumption.ignoreWhenWindowsOrMac;

public class FolderServiceIT extends TestApplication {

    private static final String FILENAME = "test_file_for_folder_service_it.txt";
    private static final String FOLDER1_NAME = "/folder1";
    private static final String FOLDER2_NAME = "/folder2";
    private static final String FOLDER_VIRTUAL_NAME = "/virtual";

    @Rule
    public TemporaryFolderUnmount temporaryFolderUnmount = new TemporaryFolderUnmount();

    @Autowired
    private FolderSettingsRepository folderSettingsRepository;
    @Autowired
    private FolderService folderService;

    private Path folder_1;
    private Path folder_2;
    private Path virtualFolder;
    private String tmpFolderPath;

    @Before
    public void setup() {
        ignoreWhenWindowsOrMac();
        tmpFolderPath = temporaryFolderUnmount.getRoot().getAbsolutePath();
        folder_1 = Paths.get(tmpFolderPath + FOLDER1_NAME);
        folder_2 = Paths.get(tmpFolderPath + FOLDER2_NAME);
        virtualFolder = Paths.get(tmpFolderPath + FOLDER_VIRTUAL_NAME);
    }

    @Test
    public void encryptedFoldersAreCreatedAndMountedAsVirtualFolderAndSharedOnNetwork() throws IOException {
        //1. encrypted folders

        folderService.createAndMountEncryptedFolder(folder_1);
        folderService.createAndMountEncryptedFolder(folder_2);

        assertTrue(exists(folder_1));
        assertTrue(exists(folder_2));

        //2. virtual folder from encrypted folders

        folderService.setupVirtualFolder(Arrays.asList(folder_1, folder_2), virtualFolder);
        createFileInFolder(FILENAME, virtualFolder);

        assertTrue(exists(virtualFolder));
        assertTrue(folderContainsFile(folder_1, FILENAME) || folderContainsFile(folder_2, FILENAME));

        //3. folder settings are stored for encrypted folders

        List<FolderSettings> allFolderSettings = folderSettingsRepository.getAllFolderSettings();

        int found = 0;
        for (FolderSettings folderSetting : allFolderSettings) {
            if (folderSetting.getFolder().equals(folder_1) || folderSetting.getFolder().equals(folder_2)) {
                found++;
            }
        }
        assertEquals(2, found);

        //4. virtual folder is shared on the network

        folderService.shareFolder(virtualFolder);

        SmbFile sharedFolder = getSmbFile(FOLDER_VIRTUAL_NAME);
        assertEquals(1, sharedFolder.list().length);
        assertEquals(FILENAME, sharedFolder.list()[0]);
    }

    @Test
    public void createAndMountEncryptedFolderAndThenUnmountItAndThenMountItAgain() throws IOException {
        Passphrase passphrase = folderService.createAndMountEncryptedFolder(folder_1);
        createFileInFolder(FILENAME, folder_1);
        assertTrue(folderContainsFile(folder_1, FILENAME));

        folderService.unmountEncryptedFolder(folder_1);
        assertFalse(Files.exists(folder_1));

        folderService.mountEncryptedFolder(getEncryptedFolder(folder_1), passphrase);
        assertTrue(folderContainsFile(folder_1, FILENAME));
    }

    @Test
    public void folderIsSharedAndUnsharedOnNetwork() throws IOException, InterruptedException {
        String shareFolderName = "/shared_folder";
        Path shareFolder = Paths.get(tmpFolderPath + shareFolderName);
        Files.createDirectory(shareFolder);
        createFileInFolder(FILENAME, shareFolder);

        folderService.shareFolder(shareFolder);

        SmbFile networkShare = getSmbFile(shareFolderName);
        assertEquals(1, networkShare.list().length);
        assertEquals(FILENAME, networkShare.list()[0]);

        folderService.unshareFolder(shareFolder);

        try {
            getSmbFile(shareFolderName).list();
            fail("expected listing files in network share to fail because network share should have been removed");
        } catch (SmbException ignore) {
        }
    }

    private Path createFileInFolder(String filename, Path folder) throws IOException {
        Path file = Paths.get(folder.toAbsolutePath().toString() + "/" + filename);
        return Files.createFile(file);
    }

    private boolean folderContainsFile(Path folder, String filename) throws IOException {
        for (Path path : Files.newDirectoryStream(folder)) {
            if (path.getFileName().toString().equals(filename)) {
                return true;
            }
        }
        return false;
    }

    private SmbFile getSmbFile(String shareFolderName) throws MalformedURLException {
        return new SmbFile(String.format("smb://127.0.0.1/%s/", shareFolderName));
    }
}