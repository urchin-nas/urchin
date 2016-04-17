package urchin.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class AutoMountFolderRepository {

    private static final Logger LOG = LoggerFactory.getLogger(AutoMountFolderRepository.class);

    public void savePassphrase(FolderSettings folderSettings, Passphrase passphrase) {
        //TODO implement - Save at remote location using SSH
    }

    public Passphrase getPassphrase(FolderSettings folderSettings) {
        //TODO implement - get from remote location using SSH
        return null;
    }
}
