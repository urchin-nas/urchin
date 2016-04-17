package urchin.service;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import urchin.domain.FolderSettings;
import urchin.domain.FolderSettingsRepository;
import urchin.domain.Passphrase;
import urchin.domain.shell.MountEncryptedFolderShellCommand;
import urchin.domain.shell.MountVirtualFolderShellCommand;
import urchin.domain.shell.UnmountFolderShellCommand;
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
import static urchin.util.EncryptedFolderUtil.getEncryptedFolder;

public class FolderServiceIT extends H2Application {

    public static final Runtime runtime = Runtime.getRuntime();
    public static final String FILENAME = "test.txt";

    @Rule
    public TemporaryFolderUmount temporaryFolderUmount = new TemporaryFolderUmount();

    private UnmountFolderShellCommand unmountFolderShellCommand;
    private FolderSettingsRepository folderSettingsRepository;
    private FolderService folderService;
    private Path folder_1;
    private Path folder_2;
    private Path virtualFolder;

    @Before
    public void setup() {
        MountEncryptedFolderShellCommand mountEncryptedFolderShellCommand = new MountEncryptedFolderShellCommand(runtime);
        MountVirtualFolderShellCommand mountVirtualFolderShellCommand = new MountVirtualFolderShellCommand(runtime);
        unmountFolderShellCommand = new UnmountFolderShellCommand(runtime);
        folderSettingsRepository = new FolderSettingsRepository(jdbcTemplate);
        folderService = new FolderService(
                mountEncryptedFolderShellCommand,
                mountVirtualFolderShellCommand,
                unmountFolderShellCommand,
                folderSettingsRepository
        );

        String tmpFolderPath = temporaryFolderUmount.getRoot().getAbsolutePath();
        folder_1 = Paths.get(tmpFolderPath + "/folder1");
        folder_2 = Paths.get(tmpFolderPath + "/folder2");
        virtualFolder = Paths.get(tmpFolderPath + "/virtual");
    }

    @Test
    public void encryptedFoldersAreCreatedAndMountedAsVirtualFolder() throws IOException {
        folderService.createAndMountEncryptedFolder(folder_1);
        folderService.createAndMountEncryptedFolder(folder_2);

        folderService.setupVirtualFolder(Arrays.asList(folder_1, folder_2), virtualFolder);

        createFileInFolder(FILENAME, virtualFolder);
        assertTrue(exists(folder_1));
        assertTrue(exists(folder_2));
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
    }

    @Test
    public void umountAndmountExistingEncryptedFolder() throws IOException {
        Passphrase passphrase = folderService.createAndMountEncryptedFolder(folder_1);
        createFileInFolder(FILENAME, folder_1);
        assertTrue(folderContainsFile(folder_1, FILENAME));

        folderService.umountEncryptedFolder(folder_1);
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