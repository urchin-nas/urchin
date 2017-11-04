package urchin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import urchin.cli.FolderCli;
import urchin.model.folder.EncryptedFolder;
import urchin.model.folder.FolderSettings;
import urchin.model.folder.Passphrase;
import urchin.repository.FolderSettingsRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.Files.createDirectories;
import static urchin.util.EncryptedFolderUtil.getEncryptedFolder;
import static urchin.util.EncryptedFolderUtil.getFolder;
import static urchin.util.PassphraseGenerator.generateEcryptfsPassphrase;

@Service
public class FolderService {

    private static final Logger LOG = LoggerFactory.getLogger(FolderService.class);

    private final FolderSettingsRepository folderSettingsRepository;
    private final FolderCli folderCli;

    @Autowired
    public FolderService(FolderSettingsRepository folderSettingsRepository, FolderCli folderCli) {
        this.folderSettingsRepository = folderSettingsRepository;
        this.folderCli = folderCli;
    }

    @Transactional
    public Passphrase createAndMountEncryptedFolder(Path folder) throws IOException {
        EncryptedFolder encryptedFolder = getEncryptedFolder(folder);
        createEncryptionFolderPair(folder, encryptedFolder);
        Passphrase passphrase = generateEcryptfsPassphrase();
        folderSettingsRepository.saveFolderSettings(encryptedFolder, folder);
        folderCli.mountEncryptedFolder(folder, encryptedFolder, passphrase);
        return passphrase;
    }

    public void mountEncryptedFolder(EncryptedFolder encryptedFolder, Passphrase passphrase) throws IOException {
        if (!Files.exists(encryptedFolder.getPath())) {
            throw new IllegalArgumentException(String.format("Encrypted folder %s does not exist", encryptedFolder.getPath()));
        }
        Path folder = getFolder(encryptedFolder);
        if (!Files.exists(folder) || isEmpty(folder)) {
            Files.createDirectories(folder);
            folderCli.mountEncryptedFolder(folder, encryptedFolder, passphrase);
        } else {
            throw new IllegalStateException(String.format("Folder %s should not exist", folder));
        }
    }

    public void unmountFolder(Path folder) throws IOException {
        if (Files.exists(folder)) {
            folderCli.unmountFolder(folder);
            if (isEmpty(folder)) {
                LOG.info("Deleting empty folder {}", folder.toAbsolutePath());
                Files.delete(folder);
            } else {
                throw new RuntimeException("Something went wrong during umount");
            }
        }
    }

    public void setupVirtualFolder(List<Path> folders, Path virtualFolder) throws IOException {
        //TODO add to db, error handling, tests etc
        setupVirtualFolder(virtualFolder);
        folderCli.mountVirtualFolder(folders, virtualFolder);
    }

    public void shareFolder(Path folder) {
        if (Files.exists(folder)) {
            folderCli.shareFolder(folder);
        } else {
            throw new IllegalArgumentException(String.format("Folder %s does not exist", folder));
        }
    }

    public void unshareFolder(Path folder) {
        if (Files.exists(folder)) {
            folderCli.unshareFolder(folder);
            folderCli.restartSamba();
        } else {
            throw new IllegalArgumentException(String.format("Folder %s does not exist", folder));
        }
    }

    private boolean isEmpty(Path folder) {
        return folder.toFile().list().length == 0;
    }

    private void setupVirtualFolder(Path virtualFolder) throws IOException {
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

    public List<FolderSettings> getFolders() {
        return folderSettingsRepository.getAllFolderSettings();
    }
}
