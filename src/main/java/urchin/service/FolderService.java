package urchin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import urchin.domain.FolderSettingsRepository;
import urchin.domain.cli.RestartSambaCommand;
import urchin.domain.cli.folder.*;
import urchin.domain.model.EncryptedFolder;
import urchin.domain.model.FolderSettings;
import urchin.domain.model.Passphrase;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.Files.createDirectories;
import static urchin.domain.util.EncryptedFolderUtil.getEncryptedFolder;
import static urchin.domain.util.EncryptedFolderUtil.getFolder;
import static urchin.domain.util.PassphraseGenerator.generateEcryptfsPassphrase;

@Service
public class FolderService {

    private static final Logger LOG = LoggerFactory.getLogger(FolderService.class);

    private final MountEncryptedFolderCommand mountEncryptedFolderCommand;
    private final MountVirtualFolderCommand mountVirtualFolderCommand;
    private final UnmountFolderCommand unmountFolderCommand;
    private final ShareFolderCommand shareFolderCommand;
    private final FolderSettingsRepository folderSettingsRepository;
    private final RestartSambaCommand restartSambaCommand;
    private final UnshareFolderCommand unshareFolderCommand;

    @Autowired
    public FolderService(
            MountEncryptedFolderCommand mountEncryptedFolderCommand,
            MountVirtualFolderCommand mountVirtualFolderCommand,
            UnmountFolderCommand unmountFolderCommand,
            ShareFolderCommand shareFolderCommand,
            UnshareFolderCommand unshareFolderCommand,
            RestartSambaCommand restartSambaCommand,
            FolderSettingsRepository folderSettingsRepository
    ) {
        this.mountEncryptedFolderCommand = mountEncryptedFolderCommand;
        this.mountVirtualFolderCommand = mountVirtualFolderCommand;
        this.unmountFolderCommand = unmountFolderCommand;
        this.shareFolderCommand = shareFolderCommand;
        this.unshareFolderCommand = unshareFolderCommand;
        this.restartSambaCommand = restartSambaCommand;
        this.folderSettingsRepository = folderSettingsRepository;
    }

    @Transactional
    public Passphrase createAndMountEncryptedFolder(Path folder) throws IOException {
        EncryptedFolder encryptedFolder = getEncryptedFolder(folder);
        createEncryptionFolderPair(folder, encryptedFolder);
        Passphrase passphrase = generateEcryptfsPassphrase();
        folderSettingsRepository.saveFolderSettings(new FolderSettings(folder, encryptedFolder));
        mountEncryptedFolderCommand.execute(folder, encryptedFolder, passphrase);
        return passphrase;
    }

    public void mountEncryptedFolder(EncryptedFolder encryptedFolder, Passphrase passphrase) throws IOException {
        if (!Files.exists(encryptedFolder.getPath())) {
            throw new IllegalArgumentException(String.format("Encrypted folder %s does not exist", encryptedFolder.getPath()));
        }
        Path folder = getFolder(encryptedFolder);
        if (!Files.exists(folder) || isEmpty(folder)) {
            Files.createDirectories(folder);
            mountEncryptedFolderCommand.execute(folder, encryptedFolder, passphrase);
        } else {
            throw new IllegalStateException(String.format("Folder %s should not exist", folder));
        }
    }

    public void unmountEncryptedFolder(Path folder) throws IOException {
        if (Files.exists(folder)) {
            unmountFolderCommand.execute(folder);
            if (isEmpty(folder)) {
                LOG.info("Deleting empty folder {}", folder.toAbsolutePath());
                Files.delete(folder);
            } else {
                throw new RuntimeException("Something went wrong during umount");
            }
        }
    }

    public void setupVirtualFolder(List<Path> folders, Path virtualFolder) throws IOException {
        //TODO error handling, tests etc
        createVirtualFolder(virtualFolder);
        mountVirtualFolderCommand.execute(folders, virtualFolder);
    }

    public void shareFolder(Path folder) {
        if (Files.exists(folder)) {
            shareFolderCommand.execute(folder);
        } else {
            throw new IllegalArgumentException(String.format("Folder %s does not exist", folder));
        }
    }

    public void unshareFolder(Path folder) {
        if (Files.exists(folder)) {
            unshareFolderCommand.execute(folder);
            restartSambaCommand.execute();
        } else {
            throw new IllegalArgumentException(String.format("Folder %s does not exist", folder));
        }
    }

    private boolean isEmpty(Path folder) {
        return folder.toFile().list().length == 0;
    }

    private void createVirtualFolder(Path virtualFolder) throws IOException {
        if (!Files.exists(virtualFolder)) {
            createDirectories(virtualFolder);
        }
    }

    private void createEncryptionFolderPair(Path folder, EncryptedFolder encryptedFolder) throws IOException {
        if (!Files.exists(folder) && !Files.exists(encryptedFolder.getPath())) {
            LOG.info("Creating folder pair {} - {}", folder, encryptedFolder);
            createDirectories(folder);
            createDirectories(encryptedFolder.getPath());
        } else {
            throw new IllegalArgumentException("At least one folder in folder pair already exist");
        }
    }
}
