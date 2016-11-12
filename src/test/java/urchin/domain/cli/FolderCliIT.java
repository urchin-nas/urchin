package urchin.domain.cli;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import urchin.RuntimeConfiguration;
import urchin.domain.cli.folder.*;
import urchin.domain.model.EncryptedFolder;
import urchin.testutil.TemporaryFolderUnmount;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static urchin.domain.util.PassphraseGenerator.generateEcryptfsPassphrase;
import static urchin.testutil.OsAssumption.ignoreWhenWindowsOrMac;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {
        RuntimeConfiguration.class,
        FolderCli.class,
        MountEncryptedFolderCommand.class,
        MountVirtualFolderCommand.class,
        RestartSambaCommand.class,
        ShareFolderCommand.class,
        UnmountFolderCommand.class,
        UnshareFolderCommand.class

})
public class FolderCliIT {

    private static final String FOLDER1_NAME = "/folder1";
    private static final String FOLDER2_NAME = "/folder2";
    private static final String ENCRYPTED_FOLDER_NAME = "/encrypted";
    private static final String FOLDER_VIRTUAL_NAME = "/virtual";

    @ClassRule
    public static TemporaryFolderUnmount temporaryFolderUnmount = new TemporaryFolderUnmount();

    @Autowired
    private FolderCli folderCli;

    private static Path folder_1;
    private static Path folder_2;
    private static EncryptedFolder encryptedFolder;
    private static Path virtualFolder;

    @BeforeClass
    public static void setup() throws IOException {
        ignoreWhenWindowsOrMac();

        String tmpFolderPath = temporaryFolderUnmount.getRoot().getAbsolutePath();

        folder_1 = Paths.get(tmpFolderPath + FOLDER1_NAME);
        folder_2 = Paths.get(tmpFolderPath + FOLDER2_NAME);
        encryptedFolder = new EncryptedFolder(Paths.get(tmpFolderPath + ENCRYPTED_FOLDER_NAME));
        virtualFolder = Paths.get(tmpFolderPath + FOLDER_VIRTUAL_NAME);

        Files.createDirectories(folder_1);
        Files.createDirectories(folder_2);
        Files.createDirectories(encryptedFolder.getPath());
        Files.createDirectories(virtualFolder);
    }

    @Test
    public void mountAndUnmountEncryptedFolderAreExecutedSuccessfully() {
        folderCli.mountEncryptedFolder(folder_1, encryptedFolder, generateEcryptfsPassphrase());
        folderCli.unmountFolder(encryptedFolder.getPath());
    }

    @Test
    public void mountAndUnmountVirtualFolderAreExecutedSuccessfully() {
        folderCli.mountVirtualFolder(Arrays.asList(folder_1, folder_2), virtualFolder);
        folderCli.unmountFolder(virtualFolder);
    }

    @Test
    public void restartSambaIsExecutedSuccessfully() {
        folderCli.restartSamba();
    }

    @Test
    public void shareAndUnshareFolderAreExecutedSuccessfully() {
        folderCli.shareFolder(folder_1);
        folderCli.unshareFolder(folder_1);
    }
}