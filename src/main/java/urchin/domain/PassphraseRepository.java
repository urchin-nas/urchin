package urchin.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import urchin.domain.model.FolderSettings;
import urchin.domain.model.Passphrase;

@Repository
public class PassphraseRepository {

    private static final Logger LOG = LoggerFactory.getLogger(PassphraseRepository.class);

    public void savePassphrase(FolderSettings folderSettings, Passphrase passphrase) {
        //TODO implement - Save at remote location using SSH
    }

    public Passphrase getPassphrase(FolderSettings folderSettings) {
        //TODO implement - get from remote location using SSH
        return null;
    }
}
