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
import org.mockito.stubbing.Answer;
import urchin.cli.FolderCli;
import urchin.cli.PermissionCli;
import urchin.cli.UserCli;
import urchin.model.folder.*;
import urchin.model.user.ImmutableLinuxUser;
import urchin.model.user.LinuxUser;
import urchin.model.user.Username;
import urchin.repository.FolderSettingsRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import static java.nio.file.Files.createDirectories;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static urchin.util.EncryptedFolderUtil.getEncryptedFolder;
import static urchin.util.PassphraseGenerator.generateEcryptfsPassphrase;

@RunWith(MockitoJUnitRunner.class)
public class FolderServiceTest {

    private static final String FOLDER_NAME = "/folder";
    private static final String ENCRYPTED_FOLDER_NAME = "/.folder";

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();
    @Mock
    private FolderCli folderCli;
    @Mock
    private UserCli userCli;
    @Mock
    private PermissionCli permissionCli;
    @Mock
    private FolderSettingsRepository folderSettingsRepository;

    @InjectMocks
    private FolderService folderService;

    private Folder folder;
    private EncryptedFolder encryptedFolder;
    private static final FolderId FOLDER_ID = FolderId.of(1);

    @Before
    public void setup() {
        folder = ImmutableFolder.of(Paths.get(temporaryFolder.getRoot() + FOLDER_NAME));
        encryptedFolder = ImmutableEncryptedFolder.of(Paths.get(temporaryFolder.getRoot() + ENCRYPTED_FOLDER_NAME));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createAndMountEncryptedFolderWhereTargetFolderAlreadyExistThrowsException() throws IOException {
        Files.createDirectories(folder.getPath());
        folderService.createAndMountEncryptedFolder(folder);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createAndMountEncryptedFolderWhereEncryptedFolderAlreadyExistThrowsException() throws IOException {
        Files.createDirectories(encryptedFolder.getPath());
        folderService.createAndMountEncryptedFolder(folder);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createAndMountEncryptedFolderWhereFolderIsInHomeDirectoryThrowsEception() {
        folderService.createAndMountEncryptedFolder(ImmutableFolder.of(Paths.get("/home/urchin")));
    }

    @Test
    public void FoldersAreCreatedAndShellCommandIsCalledWithCorrectArgumentsWhenCreateAndMountEncryptedFolder() {
        EncryptedFolder encryptedFolder = getEncryptedFolder(folder);
        when(folderSettingsRepository.saveFolderSettings(encryptedFolder, folder)).thenReturn(FolderId.of(1));
        LinuxUser linuxUser = ImmutableLinuxUser.of(Username.of("linuxUsername"));
        when(userCli.whoAmI()).thenReturn(linuxUser);
        doAnswer(createFolderAnswer(folder)).when(folderCli).createFolder(folder);
        doAnswer(createFolderAnswer(encryptedFolder)).when(folderCli).createFolder(encryptedFolder);

        CreatedFolder createdFolder = folderService.createAndMountEncryptedFolder(folder);

        ArgumentCaptor<EncryptedFolder> captor = ArgumentCaptor.forClass(EncryptedFolder.class);
        verify(folderCli).mountEncryptedFolder(eq(folder), captor.capture(), eq(createdFolder.getPassphrase()));
        verify(permissionCli).changeOwner(folder.getPath(), linuxUser);
        assertThat(captor.getValue().getPath().toAbsolutePath().toString()).isEqualTo(encryptedFolder.getPath().toAbsolutePath().toString());
        assertPathNotEqual(encryptedFolder, folder.getPath());
        assertThat(folder.isExisting()).isTrue();
        assertThat(encryptedFolder.isExisting()).isTrue();
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
        Files.createDirectories(folder.getPath());
        createFileInPath(folder.getPath());

        folderService.mountEncryptedFolder(encryptedFolder, passphrase);
    }

    @Test
    public void mountEncryptedFolder() throws IOException {
        EncryptedFolder encryptedFolder = ImmutableEncryptedFolder.of(Paths.get(temporaryFolder.getRoot() + ENCRYPTED_FOLDER_NAME));
        Files.createDirectories(encryptedFolder.getPath());
        Folder folder = encryptedFolder.toRegularFolder();
        Passphrase passphrase = generateEcryptfsPassphrase();

        folderService.mountEncryptedFolder(encryptedFolder, passphrase);

        ArgumentCaptor<Folder> captor = ArgumentCaptor.forClass(Folder.class);
        verify(folderCli).mountEncryptedFolder(captor.capture(), eq(encryptedFolder), eq(passphrase));
        assertThat(captor.getValue().toAbsolutePath()).isEqualTo(folder.toAbsolutePath());
        assertPathNotEqual(encryptedFolder, folder.getPath());
        assertThat(folder.isExisting()).isTrue();
        assertThat(encryptedFolder.isExisting()).isTrue();
    }

    @Test
    public void umountEncryptedFolderThatDoesNotExistDoesNothing() {
        folderService.unmountFolder(folder);
        verifyZeroInteractions(folderCli);
    }

    @Test(expected = RuntimeException.class)
    public void umountEncryptedFolderThrowsExceptionWhenTargetFolderIsNotEmptyAfterUmount() throws IOException {
        Files.createDirectories(folder.getPath());
        createFileInPath(folder.getPath());

        folderService.unmountFolder(folder);

        verify(folderCli).unmountFolder(folder);
    }

    @Test
    public void umountEncryptedFolderDeletesTargetFolderIfSuccessful() throws IOException {
        Files.createDirectories(folder.getPath());
        doAnswer(deleteFolderAnswer(folder)).when(folderCli).removeFolder(folder);

        folderService.unmountFolder(folder);

        verify(folderCli).unmountFolder(folder);
        assertThat(folder.isExisting()).isFalse();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shareFolderThrowsExceptionWhenFolderDoesNotExist() {
        folderService.shareFolder(folder);
    }

    @Test
    public void shareFolderCommandIsCalled() throws IOException {
        Files.createDirectories(folder.getPath());

        folderService.shareFolder(folder);

        verify(folderCli).shareFolder(eq(folder));
    }

    @Test(expected = IllegalArgumentException.class)
    public void unshareFolderThrowsExceptionWhenFolderDoesNotExist() {
        folderService.unshareFolder(folder);
    }

    @Test
    public void unshareFolderCommandAndRestartSambaCommandAreCalled() throws IOException {
        Files.createDirectories(folder.getPath());

        folderService.unshareFolder(folder);

        verify(folderCli).unshareFolder(eq(folder));
        verify(folderCli).restartSamba();
    }

    @Test(expected = IllegalStateException.class)
    public void deletingEncryptedFolderWhereFolderIsNotEmptyThrowsException() throws IOException {
        Files.createDirectories(folder.getPath());
        createFileInPath(folder.getPath());
        FolderSettings folderSettings = createFolderSettings(folder, encryptedFolder);

        when(folderSettingsRepository.getFolderSettings(FOLDER_ID)).thenReturn(folderSettings);

        folderService.deleteEncryptedFolder(FOLDER_ID);

        assertThat(folder.isExisting()).isTrue();
        assertThat(encryptedFolder.isExisting()).isTrue();
        verify(folderSettingsRepository, times(0)).removeFolderSettings(any(FolderId.class));
        verifyZeroInteractions(folderCli);
    }

    @Test(expected = IllegalStateException.class)
    public void deletingEncryptedFolderWhereEncryptedFolderIsNotEmptyThrowsException() throws IOException {
        Files.createDirectories(folder.getPath());
        Files.createDirectories(encryptedFolder.getPath());
        createFileInPath(encryptedFolder.getPath());
        FolderSettings folderSettings = createFolderSettings(folder, encryptedFolder);

        when(folderSettingsRepository.getFolderSettings(FOLDER_ID)).thenReturn(folderSettings);

        folderService.deleteEncryptedFolder(FOLDER_ID);

        assertThat(folder.isExisting()).isTrue();
        assertThat(encryptedFolder.isExisting()).isTrue();
        verify(folderSettingsRepository, times(0)).removeFolderSettings(any(FolderId.class));
        verifyZeroInteractions(folderCli);
    }

    @Test
    public void deletingEncryptedFolder() throws IOException {
        Files.createDirectories(folder.getPath());
        Files.createDirectories(encryptedFolder.getPath());
        FolderSettings folderSettings = createFolderSettings(folder, encryptedFolder);

        when(folderSettingsRepository.getFolderSettings(FOLDER_ID)).thenReturn(folderSettings);
        doAnswer(deleteFolderAnswer(folder)).when(folderCli).removeFolder(folder);
        doAnswer(deleteFolderAnswer(encryptedFolder)).when(folderCli).removeFolder(encryptedFolder);

        folderService.deleteEncryptedFolder(FOLDER_ID);

        assertThat(folder.isExisting()).isFalse();
        assertThat(encryptedFolder.isExisting()).isFalse();
        verify(folderCli).unmountFolder(folder);
        verify(folderCli).unmountFolder(encryptedFolder);
        verify(folderSettingsRepository).removeFolderSettings(FOLDER_ID);
    }

    private Answer<Void> deleteFolderAnswer(FolderWrapper folder) {
        return invocationOnMock -> {
            folder.getPath().toFile().delete();
            return null;
        };
    }

    private Answer<Void> createFolderAnswer(FolderWrapper folder) {
        return invocationOnMock -> {
            createDirectories(folder.getPath());
            return null;
        };
    }

    private FolderSettings createFolderSettings(Folder folder, EncryptedFolder encryptedFolder) {
        return ImmutableFolderSettings.builder()
                .folderId(FOLDER_ID)
                .folder(folder)
                .encryptedFolder(encryptedFolder)
                .created(LocalDateTime.now())
                .isAutoMount(true)
                .build();
    }

    private void createFileInPath(Path path) throws IOException {
        Files.createFile(Paths.get(path + "/test.txt"));
    }

    private void assertPathNotEqual(EncryptedFolder encryptedFolder, Path folder) {
        assertThat(encryptedFolder.getPath().toAbsolutePath().toString()).isNotEqualTo(folder.toAbsolutePath().toString());
    }

}