package urchin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import urchin.shell.MountEncryptedFolderShellCommand;
import urchin.shell.MountVirtualFolderShellCommand;

import java.io.File;
import java.util.List;

public class FolderService {

    private static final Logger LOG = LoggerFactory.getLogger(FolderService.class);

    private final MountEncryptedFolderShellCommand mountEncryptedFolderShellCommand;
    private final MountVirtualFolderShellCommand mountVirtualFolderShellCommand;

    @Autowired
    public FolderService(MountEncryptedFolderShellCommand mountEncryptedFolderShellCommand, MountVirtualFolderShellCommand mountVirtualFolderShellCommand) {
        this.mountEncryptedFolderShellCommand = mountEncryptedFolderShellCommand;
        this.mountVirtualFolderShellCommand = mountVirtualFolderShellCommand;
    }

    public void setupEncryptedFolder(String folderPath, String passphrase) {
        String encryptedFolderPath = folderPath.substring(0, folderPath.lastIndexOf("/")) + "/." + folderPath.substring(folderPath.lastIndexOf("/") + 1);
        createEncryptionFolders(folderPath, encryptedFolderPath);
        mountEncryptedFolderShellCommand.execute(folderPath, encryptedFolderPath, passphrase);
    }

    public void setupVirtualFolder(List<String> folderPaths, String virtualFolderPath) {
        createVirtualFolder(virtualFolderPath);
        mountVirtualFolderShellCommand.execute(folderPaths, virtualFolderPath);
    }

    private void createVirtualFolder(String virtualFolderPath) {
        File virtualFolder = new File(virtualFolderPath);
        if (!virtualFolder.exists()) {
            virtualFolder.mkdirs();
        }
    }

    private void createEncryptionFolders(String folderPath, String encryptedFolderPath) {
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
