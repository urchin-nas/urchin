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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
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

    private static String tmpFolderPath;
    private static Folder folder_1;
    private static Folder folder_2;
    private static EncryptedFolder encryptedFolder;
    private static VirtualFolder virtualFolder;

    @BeforeClass
    public static void setup() throws IOException {
        tmpFolderPath = temporaryFolderUnmount.getRoot().getAbsolutePath();

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

    @Test
    public void createAndRemoveFolder() {
        Folder folder = ImmutableFolder.of(Paths.get(tmpFolderPath + "/folder3"));
        assertThat(folder.getPath().toFile().exists()).isFalse();

        folderCli.createFolder(folder);

        assertThat(folder.getPath().toFile().exists()).isTrue();

        folderCli.removeFolder(folder);

        assertThat(folder.getPath().toFile().exists()).isFalse();
    }

    @Test
    public void setFolderImmutableAndBackToMutable() throws IOException {
        String filename1 = "testfile1";
        String filename2 = "testfile2";
        String filename3 = "testfile3";
        createFileInFolder(filename1, folder_1.getPath());
        assertThat(folderContainsFile(folder_1.getPath(), filename1)).isTrue();

        folderCli.setFolderImmutable(folder_1);
        try {
            createFileInFolder(filename2, folder_1.getPath());
            fail("expected file creation to fail because folder should be immutable");
        } catch (Exception e) {
            assertThat(folderContainsFile(folder_1.getPath(), filename2)).isFalse();
        }

        folderCli.setFolderMutable(folder_1);

        createFileInFolder(filename3, folder_1.getPath());
        assertThat(folderContainsFile(folder_1.getPath(), filename1)).isTrue();
        assertThat(folderContainsFile(folder_1.getPath(), filename2)).isFalse();
        assertThat(folderContainsFile(folder_1.getPath(), filename3)).isTrue();
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