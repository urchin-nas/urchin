package urchin.service;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import urchin.domain.Passphrase;
import urchin.shell.MountEncryptedFolderShellCommand;
import urchin.shell.MountVirtualFolderShellCommand;
import urchin.shell.UmountFolderShellCommand;
import urchin.testutil.TemporaryFolderUmount;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static java.nio.file.Files.exists;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static urchin.util.EncryptedFolderUtil.getEncryptedFolder;

public class FolderServiceIT {

    public static final Runtime runtime = Runtime.getRuntime();

    @Rule
    public TemporaryFolderUmount temporaryFolderUmount = new TemporaryFolderUmount();

    private UmountFolderShellCommand umountFolderShellCommand;
    private FolderService folderService;
    private Path folder_1;
    private Path folder_2;
    private Path virtualFolder;
    public static final String FILENAME = "test.txt";

    @Before
    public void setup() {
        MountEncryptedFolderShellCommand mountEncryptedFolderShellCommand = new MountEncryptedFolderShellCommand(runtime);
        MountVirtualFolderShellCommand mountVirtualFolderShellCommand = new MountVirtualFolderShellCommand(runtime);
        umountFolderShellCommand = new UmountFolderShellCommand(runtime);
        folderService = new FolderService(
                mountEncryptedFolderShellCommand,
                mountVirtualFolderShellCommand,
                umountFolderShellCommand
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