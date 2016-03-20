package urchin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import urchin.shell.MountEncryptedFolderShellCommand;

import java.io.File;

public class FolderService {

    private static final Logger LOG = LoggerFactory.getLogger(FolderService.class);

    private final MountEncryptedFolderShellCommand mountEncryptedFolderShellCommand;

    @Autowired
    public FolderService(MountEncryptedFolderShellCommand mountEncryptedFolderShellCommand) {
        this.mountEncryptedFolderShellCommand = mountEncryptedFolderShellCommand;
    }

    public void setupEncryptedFolder(String folderPath, String passphrase) {
        String encryptedFolderPath = folderPath.substring(0, folderPath.lastIndexOf("/")) + "/." + folderPath.substring(folderPath.lastIndexOf("/") + 1);
        createFolders(folderPath, encryptedFolderPath);
        mountEncryptedFolderShellCommand.execute(folderPath, encryptedFolderPath, passphrase);
    }

    private void createFolders(String folderPath, String encryptedFolderPath) {
        File folder = new File(folderPath);
        File encryptedFolder = new File(encryptedFolderPath);

        if (!folder.exists() && !encryptedFolder.exists()) {
            LOG.info("Creating folders");
            folder.mkdirs();
            encryptedFolder.mkdirs();
        } else {
            LOG.warn("Folders already exist");
        }
    }
}
