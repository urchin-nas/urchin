package urchin.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import urchin.model.folder.FolderSettings;
import urchin.model.folder.Passphrase;

@Repository
public class PassphraseRepository {

    private final Logger log = LoggerFactory.getLogger(PassphraseRepository.class);

    public void savePassphrase(FolderSettings folderSettings, Passphrase passphrase) {
        //TODO implement - Save at remote location using SSH
    }

    public Passphrase getPassphrase(FolderSettings folderSettings) {
        //TODO implement - get from remote location using SSH
        return null;
    }
}
