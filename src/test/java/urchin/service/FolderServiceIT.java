package urchin.service;

import jcifs.smb.SmbFile;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import urchin.domain.FolderSettingsRepository;
import urchin.domain.cli.MountEncryptedFolderCommand;
import urchin.domain.cli.MountVirtualFolderCommand;
import urchin.domain.cli.ShareFolderCommand;
import urchin.domain.cli.UnmountFolderCommand;
import urchin.domain.model.FolderSettings;
import urchin.domain.model.Passphrase;
import urchin.testutil.H2Application;
import urchin.testutil.TemporaryFolderUmount;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static java.nio.file.Files.exists;
import static junit.framework.TestCase.*;
import static urchin.domain.util.EncryptedFolderUtil.getEncryptedFolder;
import static urchin.testutil.OsAssumption.ignoreWhenWindowsOrMac;

public class FolderServiceIT extends H2Application {

    private static final Runtime runtime = Runtime.getRuntime();
    private static final String FILENAME = "test_file_for_folder_service_it.txt";
    private static final String FOLDER1_NAME = "/folder1";
    private static final String FOLDER2_NAME = "/folder2";
    private static final String FOLDER_VIRTUAL_NAME = "/virtual";

    @Rule
    public TemporaryFolderUmount temporaryFolderUmount = new TemporaryFolderUmount();

    private FolderSettingsRepository folderSettingsRepository;
    private FolderService folderService;
    private Path folder_1;
    private Path folder_2;
    private Path virtualFolder;

    @Before
    public void setup() {
        ignoreWhenWindowsOrMac();

        MountEncryptedFolderCommand mountEncryptedFolderCommand = new MountEncryptedFolderCommand(runtime);
        MountVirtualFolderCommand mountVirtualFolderCommand = new MountVirtualFolderCommand(runtime);
        UnmountFolderCommand unmountFolderCommand = new UnmountFolderCommand(runtime);
        ShareFolderCommand shareFolderCommand = new ShareFolderCommand(runtime);
        folderSettingsRepository = new FolderSettingsRepository(jdbcTemplate);
        folderService = new FolderService(
                mountEncryptedFolderCommand,
                mountVirtualFolderCommand,
                unmountFolderCommand,
                shareFolderCommand,
                folderSettingsRepository
        );

        String tmpFolderPath = temporaryFolderUmount.getRoot().getAbsolutePath();
        folder_1 = Paths.get(tmpFolderPath + FOLDER1_NAME);
        folder_2 = Paths.get(tmpFolderPath + FOLDER2_NAME);
        virtualFolder = Paths.get(tmpFolderPath + FOLDER_VIRTUAL_NAME);
    }

    @Test
    public void encryptedFoldersAreCreatedAndMountedAsVirtualFolderAndSharedOnNetwork() throws IOException {
        folderService.createAndMountEncryptedFolder(folder_1);
        folderService.createAndMountEncryptedFolder(folder_2);

        assertTrue(exists(folder_1));
        assertTrue(exists(folder_2));

        folderService.setupVirtualFolder(Arrays.asList(folder_1, folder_2), virtualFolder);
        createFileInFolder(FILENAME, virtualFolder);

        assertTrue(exists(virtualFolder));
        assertTrue(folderContainsFile(folder_1, FILENAME) || folderContainsFile(folder_2, FILENAME));

        List<FolderSettings> allFolderSettings = folderSettingsRepository.getAllFolderSettings();

        int found = 0;
        for (FolderSettings folderSetting : allFolderSettings) {
            if (folderSetting.getFolder().equals(folder_1) || folderSetting.getFolder().equals(folder_2)) {
                found++;
            }
        }
        assertEquals(2, found);

        folderService.shareFolder(virtualFolder);

        SmbFile sharedFolder = new SmbFile(String.format("smb://127.0.0.1/%s/", FOLDER_VIRTUAL_NAME));
        assertEquals(1, sharedFolder.list().length);
        assertEquals(FILENAME, sharedFolder.list()[0]);
    }

    @Test
    public void umountAndmountExistingEncryptedFolder() throws IOException {
        Passphrase passphrase = folderService.createAndMountEncryptedFolder(folder_1);
        createFileInFolder(FILENAME, folder_1);
        assertTrue(folderContainsFile(folder_1, FILENAME));

        folderService.unmountEncryptedFolder(folder_1);
        assertFalse(Files.exists(folder_1));

        folderService.mountEncryptedFolder(getEncryptedFolder(folder_1), passphrase);
        assertTrue(folderContainsFile(folder_1, FILENAME));
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
}