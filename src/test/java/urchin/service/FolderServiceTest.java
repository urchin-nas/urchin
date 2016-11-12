package urchin.service;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import urchin.domain.FolderSettingsRepository;
import urchin.domain.cli.FolderCli;
import urchin.domain.model.EncryptedFolder;
import urchin.domain.model.Passphrase;
import urchin.domain.util.EncryptedFolderUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static urchin.domain.util.EncryptedFolderUtil.getEncryptedFolder;
import static urchin.domain.util.PassphraseGenerator.generateEcryptfsPassphrase;

@RunWith(MockitoJUnitRunner.class)
public class FolderServiceTest {

    private static final String FOLDER_NAME = "/folder";
    private static final String ENCRYPTED_FOLDER_NAME = "/.folder";

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Mock
    private FolderCli folderCli;

    @Mock
    private FolderSettingsRepository folderSettingsRepository;

    @InjectMocks
    private FolderService folderService;

    private Path folder;
    private EncryptedFolder encryptedFolder;

    @Before
    public void setup() {
        folder = Paths.get(temporaryFolder.getRoot() + FOLDER_NAME);
        encryptedFolder = new EncryptedFolder(Paths.get(temporaryFolder.getRoot() + ENCRYPTED_FOLDER_NAME));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createAndMountEncryptedFolderWhereTargetFolderAlreadyExistThrowsException() throws IOException {
        Files.createDirectories(folder);
        folderService.createAndMountEncryptedFolder(folder);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createAndMountEncryptedFolderWhereEncryptedFolderAlreadyExistThrowsException() throws IOException {
        Files.createDirectories(encryptedFolder.getPath());
        folderService.createAndMountEncryptedFolder(folder);
    }

    @Test
    public void FoldersAreCreatedAndShellCommandIsCalledWithCorrectArgumentsWhenCreateAndMountEncryptedFolder() throws IOException {
        EncryptedFolder encryptedFolder = getEncryptedFolder(folder);

        Passphrase passphrase = folderService.createAndMountEncryptedFolder(folder);

        ArgumentCaptor<EncryptedFolder> captor = ArgumentCaptor.forClass(EncryptedFolder.class);
        verify(folderCli).mountEncryptedFolder(eq(folder), captor.capture(), eq(passphrase));
        assertEquals(encryptedFolder.getPath().toAbsolutePath().toString(), captor.getValue().getPath().toAbsolutePath().toString());
        assertNotEqual(encryptedFolder, folder);
        assertTrue(Files.exists(folder));
        assertTrue(Files.exists(encryptedFolder.getPath()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void mountEncryptedFolderThatDoesNotExistThrowsException() throws IOException {
        Passphrase passphrase = generateEcryptfsPassphrase();

        folderService.mountEncryptedFolder(encryptedFolder, passphrase);
    }

    @Test(expected = IllegalStateException.class)
    public void mountEncryptedFolderWhereTargetFolderIsNotEmptyThrowsException() throws IOException {
        Passphrase passphrase = generateEcryptfsPassphrase();
        Files.createDirectories(encryptedFolder.getPath());
        Files.createDirectories(folder);
        createFileInPath(folder);

        folderService.mountEncryptedFolder(encryptedFolder, passphrase);
    }

    @Test
    public void mountEncryptedFolder() throws IOException {
        EncryptedFolder encryptedFolder = new EncryptedFolder(Paths.get(temporaryFolder.getRoot() + ENCRYPTED_FOLDER_NAME));
        Files.createDirectories(encryptedFolder.getPath());
        Path folder = EncryptedFolderUtil.getFolder(encryptedFolder);
        Passphrase passphrase = generateEcryptfsPassphrase();

        folderService.mountEncryptedFolder(encryptedFolder, passphrase);

        ArgumentCaptor<Path> captor = ArgumentCaptor.forClass(Path.class);
        verify(folderCli).mountEncryptedFolder(captor.capture(), eq(encryptedFolder), eq(passphrase));
        assertEquals(folder.toAbsolutePath().toString(), captor.getValue().toAbsolutePath().toString());
        assertNotEqual(encryptedFolder, folder);
        assertTrue(Files.exists(folder));
        assertTrue(Files.exists(encryptedFolder.getPath()));
    }

    @Test
    public void umountEncryptedFolderThatDoesNotExistDoesNothing() throws IOException {
        folderService.unmountEncryptedFolder(folder);
        verifyZeroInteractions(folderCli);
    }

    @Test(expected = RuntimeException.class)
    public void umountEncryptedFolderThrowsExceptionWhenTargetFolderIsNotEmptyAfterUmount() throws IOException {
        Files.createDirectories(folder);
        createFileInPath(folder);

        folderService.unmountEncryptedFolder(folder);

        verify(folderCli).unmountFolder(folder);
    }

    @Test
    public void umountEncryptedFolderDeletesTargetFolderIfSuccessful() throws IOException {
        Files.createDirectories(folder);

        folderService.unmountEncryptedFolder(folder);

        verify(folderCli).unmountFolder(folder);
        assertFalse(Files.exists(folder));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shareFolderThrowsExceptionWhenFolderDoesNotExist() {
        folderService.shareFolder(folder);
    }

    @Test
    public void shareFolderCommandIsCalled() throws IOException {
        Files.createDirectories(folder);

        folderService.shareFolder(folder);

        verify(folderCli).shareFolder(eq(folder));
    }

    @Test(expected = IllegalArgumentException.class)
    public void unshareFolderThrowsExceptionWhenFolderDoesNotExist() {
        folderService.unshareFolder(folder);
    }

    @Test
    public void unshareFolderCommandAndRestartSambaCommandAreCalled() throws IOException {
        Files.createDirectories(folder);

        folderService.unshareFolder(folder);

        verify(folderCli).unshareFolder(eq(folder));
        verify(folderCli).restartSamba();
    }

    private void createFileInPath(Path path) throws IOException {
        Files.createFile(Paths.get(path + "/test.txt"));
    }

    private void assertNotEqual(EncryptedFolder encryptedFolder, Path folder) {
        assertNotEquals(encryptedFolder.getPath().toAbsolutePath().toString(), folder.toAbsolutePath().toString());
    }

}