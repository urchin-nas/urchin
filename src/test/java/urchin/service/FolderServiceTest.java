package urchin.service;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import urchin.domain.EncryptedFolder;
import urchin.domain.FolderSettingsRepository;
import urchin.domain.Passphrase;
import urchin.domain.shell.MountEncryptedFolderCommand;
import urchin.domain.shell.MountVirtualFolderCommand;
import urchin.domain.shell.UnmountFolderCommand;
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

    public static final String FOLDER_NAME = "/folder";
    public static final String ENCRYPTED_FOLDER_NAME = "/.folder";

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Mock
    private MountEncryptedFolderCommand mountEncryptedFolderCommand;

    @Mock
    private MountVirtualFolderCommand mountVirtualFolderCommand;

    @Mock
    private UnmountFolderCommand unmountFolderCommand;

    @Mock
    private FolderSettingsRepository folderSettingsRepository;

    private FolderService folderService;
    private Path folder;
    private EncryptedFolder encryptedFolder;

    @Before
    public void setup() {
        folderService = new FolderService(mountEncryptedFolderCommand, mountVirtualFolderCommand, unmountFolderCommand, folderSettingsRepository);
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
        verify(mountEncryptedFolderCommand).execute(eq(folder), captor.capture(), eq(passphrase));
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
        verify(mountEncryptedFolderCommand).execute(captor.capture(), eq(encryptedFolder), eq(passphrase));
        assertEquals(folder.toAbsolutePath().toString(), captor.getValue().toAbsolutePath().toString());
        assertNotEqual(encryptedFolder, folder);
        assertTrue(Files.exists(folder));
        assertTrue(Files.exists(encryptedFolder.getPath()));
    }

    @Test
    public void umountEncryptedFolderThatDoesNotExistDoesNothing() throws IOException {
        folderService.unmountEncryptedFolder(folder);
        verifyZeroInteractions(unmountFolderCommand);
    }

    @Test(expected = RuntimeException.class)
    public void umountEncryptedFolderThrowsExceptionWhenTargetFolderIsNotEmptyAfterUmount() throws IOException {
        Files.createDirectories(folder);
        createFileInPath(folder);

        folderService.unmountEncryptedFolder(folder);

        verify(unmountFolderCommand).execute(folder);
    }

    @Test
    public void umountEncryptedFolderDeletesTargetFolderIfSuccessful() throws IOException {
        Files.createDirectories(folder);

        folderService.unmountEncryptedFolder(folder);

        verify(unmountFolderCommand).execute(folder);
        assertFalse(Files.exists(folder));
    }

    private void createFileInPath(Path path) throws IOException {
        Files.createFile(Paths.get(path + "/test.txt"));
    }

    private void assertNotEqual(EncryptedFolder encryptedFolder, Path folder) {
        assertNotEquals(encryptedFolder.getPath().toAbsolutePath().toString(), folder.toAbsolutePath().toString());
    }

}