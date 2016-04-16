package urchin.domain;

import java.nio.file.Path;
import java.time.LocalDateTime;

public class FolderSettings {

    private int id;
    private Path folder;
    private EncryptedFolder encryptedFolder;
    private LocalDateTime created;

    public FolderSettings() {
    }

    public FolderSettings(Path folder, EncryptedFolder encryptedFolder) {
        this.folder = folder;
        this.encryptedFolder = encryptedFolder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Path getFolder() {
        return folder;
    }

    public void setFolder(Path folder) {
        this.folder = folder;
    }

    public EncryptedFolder getEncryptedFolder() {
        return encryptedFolder;
    }

    public void setEncryptedFolder(EncryptedFolder encryptedFolder) {
        this.encryptedFolder = encryptedFolder;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
