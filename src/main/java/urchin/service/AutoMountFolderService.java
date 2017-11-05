package urchin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import urchin.cli.FolderCli;
import urchin.model.folder.FolderSettings;
import urchin.model.folder.Passphrase;
import urchin.repository.PassphraseRepository;

@Service
public class AutoMountFolderService {

    private static final Logger LOG = LoggerFactory.getLogger(AutoMountFolderService.class);

    private final PassphraseRepository passphraseRepository;
    private final FolderCli folderCli;

    @Autowired
    public AutoMountFolderService(PassphraseRepository passphraseRepository, FolderCli folderCli) {
        this.passphraseRepository = passphraseRepository;
        this.folderCli = folderCli;
    }

    public void setup(FolderSettings folderSettings, Passphrase passphrase) {
        passphraseRepository.savePassphrase(folderSettings, passphrase);
        LOG.info("Auto mounting of encrypted folder {} enabled", folderSettings.getFolder());
    }

    public void mount(FolderSettings folderSettings) {
        Passphrase passphrase = passphraseRepository.getPassphrase(folderSettings);
        folderCli.mountEncryptedFolder(folderSettings.getFolder().getPath(), folderSettings.getEncryptedFolder(), passphrase);
    }
}
