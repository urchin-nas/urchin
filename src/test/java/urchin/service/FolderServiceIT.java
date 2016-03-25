package urchin.service;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import urchin.shell.MountVirtualFolderShellCommand;
import urchin.shell.SetupAndMountEncryptedFolderShellCommand;
import urchin.shell.UmountFolderShellCommand;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

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
        File folder_1 = new File(tmpFolderPath + "/folder1");
        File folder_2 = new File(tmpFolderPath + "/folder2");
        File virtualFolder = new File(tmpFolderPath + "/virtual");
        folderService.setupEncryptedFolder(folder_1);
        folderService.setupEncryptedFolder(folder_2);
        try {
            folderService.setupVirtualFolder(Arrays.asList(folder_1, folder_2), virtualFolder);

            File file = createFileInVirtualFolder(filename, virtualFolder);
            assertTrue(folder_1.exists());
            assertTrue(folder_2.exists());
            assertTrue(virtualFolder.exists());
            assertTrue(file.exists());
            assertTrue(containsFile(folder_1, filename) || containsFile(folder_2, filename));
        } finally {
            umountFolderShellCommand.execute(virtualFolder);
            umountFolderShellCommand.execute(folder_1);
            umountFolderShellCommand.execute(folder_2);
        }
    }

    private File createFileInVirtualFolder(String filename, File virtualFolder) throws IOException {
        File file = new File(virtualFolder.getAbsolutePath() + "/" + filename);
        file.createNewFile();
        return file;
    }

    private boolean containsFile(File folder, String filename) throws IOException {
        return Files.walk(folder.toPath()).anyMatch(filePath ->
                Files.isRegularFile(filePath) && filePath.getFileName().toString().equals(filename)
        );
    }
}