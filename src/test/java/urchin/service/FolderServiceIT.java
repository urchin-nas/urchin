package urchin.service;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import urchin.shell.MountVirtualFolderShellCommand;
import urchin.shell.SetupAndMountEncryptedFolderShellCommand;
import urchin.shell.UmountFolderShellCommand;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static java.nio.file.Files.exists;
import static junit.framework.TestCase.assertTrue;

public class FolderServiceIT {

    public static final Runtime runtime = Runtime.getRuntime();

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private UmountFolderShellCommand umountFolderShellCommand;
    private FolderService folderService;

    @Before
    public void setup() {
        SetupAndMountEncryptedFolderShellCommand setupAndMountEncryptedFolderShellCommand = new SetupAndMountEncryptedFolderShellCommand(runtime);
        MountVirtualFolderShellCommand mountVirtualFolderShellCommand = new MountVirtualFolderShellCommand(runtime);
        umountFolderShellCommand = new UmountFolderShellCommand(runtime);
        folderService = new FolderService(
                setupAndMountEncryptedFolderShellCommand,
                mountVirtualFolderShellCommand,
                umountFolderShellCommand
        );
    }

    @Test
    public void encryptedFoldersAreCreatedAndMountedAsVirtualFolder() throws IOException {
        String tmpFolderPath = temporaryFolder.getRoot().getAbsolutePath();
        String filename = "test.txt";
        Path folder_1 = Paths.get(tmpFolderPath + "/folder1");
        Path folder_2 = Paths.get(tmpFolderPath + "/folder2");
        Path virtualFolder = Paths.get(tmpFolderPath + "/virtual");
        folderService.setupEncryptedFolder(folder_1);
        folderService.setupEncryptedFolder(folder_2);
        try {
            folderService.setupVirtualFolder(Arrays.asList(folder_1, folder_2), virtualFolder);

            createFileInVirtualFolder(filename, virtualFolder);
            assertTrue(exists(folder_1));
            assertTrue(exists(folder_2));
            assertTrue(exists(virtualFolder));
            assertTrue(containsPath(folder_1, filename) || containsPath(folder_2, filename));
        } finally {
            umountFolderShellCommand.execute(virtualFolder);
            umountFolderShellCommand.execute(folder_1);
            umountFolderShellCommand.execute(folder_2);
        }
    }

    private Path createFileInVirtualFolder(String filename, Path virtualFolder) throws IOException {
        Path file = Paths.get(virtualFolder.toAbsolutePath().toString() + "/" + filename);
        return Files.createFile(file);
    }

    private boolean containsPath(Path folder, String filename) throws IOException {
        for (Path path : Files.newDirectoryStream(folder)) {
            if (path.getFileName().toString().equals(filename)) {
                return true;
            }
        }
        return false;
    }
}