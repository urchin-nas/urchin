package urchin.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import urchin.cli.FolderCli;
import urchin.model.folder.*;
import urchin.repository.PassphraseRepository;
import urchin.util.PassphraseGenerator;

import java.nio.file.Paths;
import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AutoMountFolderServiceTest {

    private static final FolderSettings FOLDER_SETTINGS = ImmutableFolderSettings.builder()
            .folderId(FolderId.of(1))
            .folder(ImmutableFolder.of(Paths.get("/some/path")))
            .encryptedFolder(ImmutableEncryptedFolder.of(Paths.get("/some/.path")))
            .created(LocalDateTime.now())
            .isAutoMount(false)
            .build();
    private static final Passphrase PASSPHRASE = PassphraseGenerator.generateEcryptfsPassphrase();

    @Mock
    private PassphraseRepository passphraseRepository;

    @Mock
    private FolderCli folderCli;

    private AutoMountFolderService autoMountFolderService;


    @Before
    public void setup() {
        autoMountFolderService = new AutoMountFolderService(passphraseRepository, folderCli);
    }

    @Test
    public void autoMountFolderRepositoryIsCalledWhenDoingSetup() {
        autoMountFolderService.setup(FOLDER_SETTINGS, PASSPHRASE);

        verify(passphraseRepository).savePassphrase(FOLDER_SETTINGS, PASSPHRASE);
    }

    @Test
    public void mountEncryptedFolderIsCalledWithCorrectly() {
        when(passphraseRepository.getPassphrase(FOLDER_SETTINGS)).thenReturn(PASSPHRASE);

        autoMountFolderService.mount(FOLDER_SETTINGS);

        verify(folderCli).mountEncryptedFolder(FOLDER_SETTINGS.getFolder(), FOLDER_SETTINGS.getEncryptedFolder(), PASSPHRASE);
    }

}