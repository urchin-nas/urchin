package urchin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import urchin.cli.FolderCli;
import urchin.model.folder.*;
import urchin.repository.FolderSettingsRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static urchin.util.PassphraseGenerator.generateEcryptfsPassphrase;

@Service
public class FolderService {

    private final Logger log = LoggerFactory.getLogger(FolderService.class);
    private final FolderSettingsRepository folderSettingsRepository;
    private final FolderCli folderCli;

    @Autowired
    public FolderService(FolderSettingsRepository folderSettingsRepository, FolderCli folderCli) {
        this.folderSettingsRepository = folderSettingsRepository;
        this.folderCli = folderCli;
    }

    @Transactional
    public CreatedFolder createAndMountEncryptedFolder(Folder folder) {
        if (folder.getPath().startsWith("/home/")) {
            throw new IllegalArgumentException("Folder path must not start with /home/");
        }

        EncryptedFolder encryptedFolder = folder.toEncryptedFolder();
        createEncryptionFolderPair(folder, encryptedFolder);
        Passphrase passphrase = generateEcryptfsPassphrase();
        FolderId folderId = folderSettingsRepository.saveFolderSettings(encryptedFolder, folder);
        folderCli.mountEncryptedFolder(folder, encryptedFolder, passphrase);

        return ImmutableCreatedFolder.builder()
                .folderId(folderId)
                .passphrase(passphrase)
                .build();
    }

    @Transactional
    public void deleteEncryptedFolder(FolderId folderId) {
        FolderSettings folderSettings = folderSettingsRepository.getFolderSettings(folderId);
        Folder folder = folderSettings.getFolder();

        if (!folder.isEmpty()) {
            throw new IllegalStateException(String.format("Folder %s must be empty before it can be deleted", folder));
        }

        EncryptedFolder encryptedFolder = folderSettings.getEncryptedFolder();
        if (!encryptedFolder.isEmpty()) {
            throw new IllegalStateException(String.format("EncryptedFolder %s must be empty before it can be deleted", encryptedFolder));
        }

        log.info("Deleting encrypted folder {}", folderSettings);
        folderSettingsRepository.removeFolderSettings(folderId);
        try {
            unmountFolder(folder);
        } catch (Exception ignore) {
        }
        try {
            unmountEncryptedFolder(encryptedFolder);
        } catch (Exception ignore) {
        }

        deleteFolder(encryptedFolder);

        if (folder.isExisting() || encryptedFolder.isExisting()) {
            throw new RuntimeException("failed to delete folders");
        }

    }

    public void mountEncryptedFolder(EncryptedFolder encryptedFolder, Passphrase passphrase) throws IOException {
        if (!Files.exists(encryptedFolder.getPath())) {
            throw new IllegalArgumentException(String.format("EncryptedFolder %s does not exist", encryptedFolder.getPath()));
        }
        Folder folder = encryptedFolder.toRegularFolder();
        if (!Files.exists(folder.getPath()) || folder.isEmpty()) {
            Files.createDirectories(folder.getPath());
            folderCli.mountEncryptedFolder(folder, encryptedFolder, passphrase);
        } else {
            throw new IllegalStateException(String.format("Folder %s should not exist", folder));
        }
    }

    public void unmountFolder(Folder folder) {
        if (Files.exists(folder.getPath())) {
            folderCli.unmountFolder(folder);
            deleteFolder(folder);
        }
    }

    public void setupVirtualFolder(List<Folder> folders, VirtualFolder virtualFolder) throws IOException {
        //TODO add to db, error handling, tests etc
        setupVirtualFolder(virtualFolder);
        folderCli.mountVirtualFolder(folders, virtualFolder);
    }

    public void shareFolder(Folder folder) {
        if (folder.isExisting()) {
            folderCli.shareFolder(folder);
        } else {
            throw new IllegalArgumentException(String.format("Folder %s does not exist", folder));
        }
    }

    public void unshareFolder(Folder folder) {
        if (folder.isExisting()) {
            folderCli.unshareFolder(folder);
            folderCli.restartSamba();
        } else {
            throw new IllegalArgumentException(String.format("Folder %s does not exist", folder));
        }
    }

    public List<FolderSettings> getFolders() {
        return folderSettingsRepository.getFoldersSettings();
    }

    public FolderSettings getFolder(FolderId folderId) {
        return folderSettingsRepository.getFolderSettings(folderId);
    }

    private void unmountEncryptedFolder(EncryptedFolder encryptedFolder) {
        if (Files.exists(encryptedFolder.getPath())) {
            folderCli.unmountFolder(encryptedFolder);
        }
    }

    private void deleteFolder(FolderWrapper folder) {
        if (folder.isEmpty()) {
            log.info("Deleting empty folder {}", folder.toAbsolutePath());
            folderCli.removeFolder(folder);
        } else {
            throw new IllegalStateException(String.format("%s must be empty before it can be deleted", folder));
        }
    }

    private void setupVirtualFolder(VirtualFolder virtualFolder) {
        if (!virtualFolder.isExisting()) {
            folderCli.createFolder(virtualFolder);
        }
    }

    private void createEncryptionFolderPair(Folder folder, EncryptedFolder encryptedFolder) {
        if (!folder.isExisting() && !encryptedFolder.isExisting()) {
            log.info("Creating folder pair {} - {}", folder, encryptedFolder);
            folderCli.createFolder(folder);
            folderCli.createFolder(encryptedFolder);
        } else {
            throw new IllegalArgumentException("At least one folder in folder pair already exist");
        }
    }
}
