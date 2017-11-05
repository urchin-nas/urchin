package urchin.cli;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import urchin.model.folder.*;
import urchin.testutil.CliTestConfiguration;
import urchin.testutil.TemporaryFolderUnmount;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static urchin.util.PassphraseGenerator.generateEcryptfsPassphrase;

@RunWith(SpringRunner.class)
@Import(CliTestConfiguration.class)
public class FolderCliIT {

    private static final String FOLDER1_NAME = "/folder1";
    private static final String FOLDER2_NAME = "/folder2";
    private static final String ENCRYPTED_FOLDER_NAME = "/encrypted";
    private static final String FOLDER_VIRTUAL_NAME = "/virtual";

    @ClassRule
    public static TemporaryFolderUnmount temporaryFolderUnmount = new TemporaryFolderUnmount();

    @Autowired
    private FolderCli folderCli;

    private static Folder folder_1;
    private static Folder folder_2;
    private static EncryptedFolder encryptedFolder;
    private static VirtualFolder virtualFolder;

    @BeforeClass
    public static void setup() throws IOException {
        String tmpFolderPath = temporaryFolderUnmount.getRoot().getAbsolutePath();

        folder_1 = ImmutableFolder.of(Paths.get(tmpFolderPath + FOLDER1_NAME));
        folder_2 = ImmutableFolder.of(Paths.get(tmpFolderPath + FOLDER2_NAME));
        encryptedFolder = ImmutableEncryptedFolder.of(Paths.get(tmpFolderPath + ENCRYPTED_FOLDER_NAME));
        virtualFolder = ImmutableVirtualFolder.of(Paths.get(tmpFolderPath + FOLDER_VIRTUAL_NAME));

        Files.createDirectories(folder_1.getPath());
        Files.createDirectories(folder_2.getPath());
        Files.createDirectories(encryptedFolder.getPath());
        Files.createDirectories(virtualFolder.getPath());
    }

    @Test
    public void mountAndUnmountEncryptedFolderAreExecutedSuccessfully() {
        folderCli.mountEncryptedFolder(folder_1, encryptedFolder, generateEcryptfsPassphrase());
        folderCli.unmountFolder(encryptedFolder);
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