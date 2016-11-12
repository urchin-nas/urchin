package urchin.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import urchin.domain.PassphraseRepository;
import urchin.domain.cli.FolderCli;
import urchin.domain.model.EncryptedFolder;
import urchin.domain.model.FolderSettings;
import urchin.domain.model.Passphrase;
import urchin.domain.util.PassphraseGenerator;

import java.nio.file.Paths;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AutoMountFolderServiceTest {

    private static final FolderSettings FOLDER_SETTINGS = new FolderSettings(Paths.get("/some/path"), new EncryptedFolder(Paths.get("/some/.path")));
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