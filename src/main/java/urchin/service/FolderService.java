package urchin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import urchin.domain.Passphrase;
import urchin.shell.MountVirtualFolderShellCommand;
import urchin.shell.SetupAndMountEncryptedFolderShellCommand;
import urchin.shell.UmountFolderShellCommand;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.file.Files.createDirectories;


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

    public void setupEncryptedFolder(Path folder) throws IOException {
        Path encryptedFolder = getEncryptedFolder(folder);
        createEncryptionFolderPair(folder, encryptedFolder);
        Passphrase passphrase = setupAndMountEncryptedFolderShellCommand.execute(folder, encryptedFolder);
    }

    public void setupVirtualFolder(List<Path> folders, Path virtualFolder) throws IOException {
        createVirtualFolder(virtualFolder);
        mountVirtualFolderShellCommand.execute(folders, virtualFolder);
    }

    private Path getEncryptedFolder(Path folder) {
        String path = folder.toAbsolutePath().toString();
        String encryptedFolderPath = path.substring(0, path.lastIndexOf("/")) + "/." + path.substring(path.lastIndexOf("/") + 1);
        LOG.debug("Encrypted folder path {}", encryptedFolderPath);
        return Paths.get(encryptedFolderPath);
    }

    private void createVirtualFolder(Path virtualFolder) throws IOException {
        if (!Files.exists(virtualFolder)) {
            createDirectories(virtualFolder);
        }
    }

    private void createEncryptionFolderPair(Path folder, Path encryptedFolder) throws IOException {
        if (!Files.exists(folder) && !Files.exists(encryptedFolder)) {
            LOG.info("Creating folder pair {} - {}", folder, encryptedFolder);
            createDirectories(folder);
            createDirectories(encryptedFolder);
        } else {
            LOG.warn("Folder pair already exist");
        }
    }
}
