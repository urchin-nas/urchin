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

import static java.nio.file.Files.createDirectories;
import static urchin.util.PassphraseGenerator.generateEcryptfsPassphrase;

@Service
public class FolderService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final FolderSettingsRepository folderSettingsRepository;
    private final FolderCli folderCli;

    @Autowired
    public FolderService(FolderSettingsRepository folderSettingsRepository, FolderCli folderCli) {
        this.folderSettingsRepository = folderSettingsRepository;
        this.folderCli = folderCli;
    }

    @Transactional
    public CreatedFolder createAndMountEncryptedFolder(Folder folder) throws IOException {
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
    public void deleteEncryptedFolder(FolderId folderId) throws IOException {
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

        unmountFolder(folder);
        unmountEncryptedFolder(encryptedFolder);
        deleteFolder(encryptedFolder);
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

    public void unmountFolder(Folder folder) throws IOException {
        if (Files.exists(folder.getPath())) {
            folderCli.unmountFolder(folder);
            deleteFolder(folder);
        }
    }

    private void unmountEncryptedFolder(EncryptedFolder encryptedFolder) throws IOException {
        if (Files.exists(encryptedFolder.getPath())) {
            folderCli.unmountFolder(encryptedFolder);
        }
    }

    private <T extends FolderWrapper> void deleteFolder(T folder) throws IOException {
        if (folder.isEmpty()) {
            log.info("Deleting empty folder {}", folder.toAbsolutePath());
            Files.delete(folder.getPath());
        } else {
            throw new IllegalStateException(String.format("%s must be empty before it can be deleted", folder));
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

    private void setupVirtualFolder(VirtualFolder virtualFolder) throws IOException {
        if (!virtualFolder.isExisting()) {
            createDirectories(virtualFolder.getPath());
        }
    }

    private void createEncryptionFolderPair(Folder folder, EncryptedFolder encryptedFolder) throws IOException {
        if (!folder.isExisting() && !Files.exists(encryptedFolder.getPath())) {
            log.info("Creating folder pair {} - {}", folder, encryptedFolder);
            createDirectories(folder.getPath());
            createDirectories(encryptedFolder.getPath());
        } else {
            throw new IllegalArgumentException("At least one folder in folder pair already exist");
        }
    }

    public List<FolderSettings> getFolders() {
        return folderSettingsRepository.getFoldersSettings();
    }

    public FolderSettings getFolder(FolderId folderId) {
        return folderSettingsRepository.getFolderSettings(folderId);
    }
}
