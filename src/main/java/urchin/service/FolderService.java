package urchin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import urchin.domain.Passphrase;
import urchin.shell.MountVirtualFolderShellCommand;
import urchin.shell.SetupAndMountEncryptedFolderShellCommand;
import urchin.shell.UmountFolderShellCommand;

import java.io.File;
import java.util.List;

public class FolderService {

    private static final Logger LOG = LoggerFactory.getLogger(FolderService.class);

    private final SetupAndMountEncryptedFolderShellCommand setupAndMountEncryptedFolderShellCommand;
    private final MountVirtualFolderShellCommand mountVirtualFolderShellCommand;
    private final UmountFolderShellCommand umountFolderShellCommand;

    @Autowired
    public FolderService(
            SetupAndMountEncryptedFolderShellCommand setupAndMountEncryptedFolderShellCommand,
            MountVirtualFolderShellCommand mountVirtualFolderShellCommand,
            UmountFolderShellCommand umountFolderShellCommand
    ) {
        this.setupAndMountEncryptedFolderShellCommand = setupAndMountEncryptedFolderShellCommand;
        this.mountVirtualFolderShellCommand = mountVirtualFolderShellCommand;
        this.umountFolderShellCommand = umountFolderShellCommand;
    }

    public void setupEncryptedFolder(File folder) {
        File encryptedFolder = getEncryptedFolder(folder);
        createEncryptionFolderPair(folder, encryptedFolder);
        Passphrase passphrase = setupAndMountEncryptedFolderShellCommand.execute(folder, encryptedFolder);
    }

    public void setupVirtualFolder(List<File> folders, File virtualFolder) {
        createVirtualFolder(virtualFolder);
        mountVirtualFolderShellCommand.execute(folders, virtualFolder);
    }

    private File getEncryptedFolder(File folder) {
        String path = folder.getAbsolutePath();
        String encryptedFolderPath = path.substring(0, path.lastIndexOf("/")) + "/." + path.substring(path.lastIndexOf("/") + 1);
        LOG.debug("Encrypted folder path {}", encryptedFolderPath);
        return new File(encryptedFolderPath);
    }

    private void createVirtualFolder(File virtualFolder) {
        if (!virtualFolder.exists()) {
            virtualFolder.mkdirs();
        }
    }

    private boolean createEncryptionFolderPair(File folder, File encryptedFolder) {
        if (!folder.exists() && !encryptedFolder.exists()) {
            LOG.info("Creating folder pair {} - {}", folder, encryptedFolder);
            return folder.mkdirs() && encryptedFolder.mkdirs();
        } else {
            LOG.warn("Folder pair already exist");
        }
        return false;
    }
}
