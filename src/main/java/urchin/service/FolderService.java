package urchin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import urchin.cli.FolderCli;
import urchin.cli.PermissionCli;
import urchin.cli.UserCli;
import urchin.model.folder.*;
import urchin.model.user.LinuxUser;
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
    private final UserCli userCli;
    private final PermissionCli permissionCli;

    @Autowired
    public FolderService(FolderSettingsRepository folderSettingsRepository,
                         FolderCli folderCli,
                         UserCli userCli,
                         PermissionCli permissionCli) {
        this.folderSettingsRepository = folderSettingsRepository;
        this.folderCli = folderCli;
        this.userCli = userCli;
        this.permissionCli = permissionCli;
    }

    @Transactional
    public CreatedFolder createAndMountEncryptedFolder(Folder folder) {
        if (folder.getPath().startsWith("/home/")) {
            throw new IllegalArgumentException("Folder path must not start with /home/");
        }
        LinuxUser owner = userCli.whoAmI();
        EncryptedFolder encryptedFolder = folder.toEncryptedFolder();
        createEncryptionFolderPair(folder, encryptedFolder, owner);
        Passphrase passphrase = generateEcryptfsPassphrase();
        FolderId folderId = folderSettingsRepository.saveFolderSettings(encryptedFolder, folder);
        folderCli.mountEncryptedFolder(folder, encryptedFolder, passphrase);
        folderCli.unmountFolder(encryptedFolder);

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
        } catch (Exception e) {
            log.warn("Failed to unmount {}", folder, e);
        }
        try {
            unmountEncryptedFolder(encryptedFolder);
        } catch (Exception e) {
            log.warn("Failed to unmount {}", encryptedFolder, e);
        }

        folderCli.setFolderMutable(folder);

        deleteFolder(folder);
        deleteFolder(encryptedFolder);

        if (folder.isExisting() || encryptedFolder.isExisting()) {
            throw new IllegalStateException("failed to delete folders");
        }
    }

    public void mountEncryptedFolder(FolderId folderId, Passphrase passphrase) throws IOException {
        FolderSettings folderSettings = folderSettingsRepository.getFolderSettings(folderId);
        EncryptedFolder encryptedFolder = folderSettings.getEncryptedFolder();

        if (!encryptedFolder.isExisting()) {
            throw new IllegalArgumentException(String.format("EncryptedFolder %s does not exist", encryptedFolder.getPath()));
        }
        Folder folder = encryptedFolder.toRegularFolder();
        if (!folder.isExisting() || folder.isEmpty()) {
            Files.createDirectories(folder.getPath());
            folderCli.mountEncryptedFolder(folder, encryptedFolder, passphrase);
        } else {
            throw new IllegalStateException(String.format("Folder %s should not exist", folder));
        }
    }

    public void unmountEncryptedFolder(FolderId folderId) {
        FolderSettings folderSettings = folderSettingsRepository.getFolderSettings(folderId);
        folderCli.unmountFolder(folderSettings.getEncryptedFolder());
    }

    public void unmountFolder(Folder folder) {
        if (folder.isExisting()) {
            folderCli.unmountFolder(folder);
        }
    }

    public void setupVirtualFolder(List<Folder> folders, VirtualFolder virtualFolder) {
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
        if (encryptedFolder.isExisting()) {
            folderCli.unmountFolder(encryptedFolder);
        }
    }

    private void deleteFolder(FolderWrapper folder) {
        if (folder.isEmpty()) {
            log.info("Deleting empty folder {}", folder);
            folderCli.removeFolder(folder);
        } else {
            throw new IllegalStateException(String.format("%s must be empty before it can be deleted", folder));
        }
    }

    private void setupVirtualFolder(VirtualFolder virtualFolder) {
        if (!virtualFolder.isExisting()) {
            LinuxUser linuxUser = userCli.whoAmI();
            folderCli.createFolder(virtualFolder);
            permissionCli.changeOwner(virtualFolder.getPath(), linuxUser);
        }
    }

    private void createEncryptionFolderPair(Folder folder, EncryptedFolder encryptedFolder, LinuxUser linuxUser) {
        if (!folder.isExisting() && !encryptedFolder.isExisting()) {
            log.info("Creating folder pair {} - {}", folder, encryptedFolder);
            folderCli.createFolder(folder);
            permissionCli.changeOwner(folder.getPath(), linuxUser);
            folderCli.setFolderImmutable(folder);
            folderCli.createFolder(encryptedFolder);
            permissionCli.changeOwner(encryptedFolder.getPath(), linuxUser);
        } else {
            throw new IllegalArgumentException("At least one folder in folder pair already exist");
        }
    }
}
