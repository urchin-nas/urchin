package urchin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import urchin.domain.FolderSettings;
import urchin.domain.Passphrase;
import urchin.domain.PassphraseRepository;
import urchin.domain.shell.MountEncryptedFolderCommand;

@Service
public class AutoMountFolderService {

    private static final Logger LOG = LoggerFactory.getLogger(AutoMountFolderService.class);

    private final PassphraseRepository passphraseRepository;
    private final MountEncryptedFolderCommand mountEncryptedFolderCommand;

    @Autowired
    public AutoMountFolderService(PassphraseRepository passphraseRepository, MountEncryptedFolderCommand mountEncryptedFolderCommand) {
        this.passphraseRepository = passphraseRepository;
        this.mountEncryptedFolderCommand = mountEncryptedFolderCommand;
    }

    public void setup(FolderSettings folderSettings, Passphrase passphrase) {
        passphraseRepository.savePassphrase(folderSettings, passphrase);
        LOG.info("Auto mounting of encrypted folder {} enabled", folderSettings.getFolder());
    }

    public void mount(FolderSettings folderSettings) {
        Passphrase passphrase = passphraseRepository.getPassphrase(folderSettings);
        mountEncryptedFolderCommand.execute(folderSettings.getFolder(), folderSettings.getEncryptedFolder(), passphrase);
    }
}
