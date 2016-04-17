package urchin.domain.model;

import java.nio.file.Path;
import java.time.LocalDateTime;

public class FolderSettings {

    private int id;
    private final Path folder;
    private final EncryptedFolder encryptedFolder;
    private LocalDateTime created;
    private boolean autoMount;

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

    public EncryptedFolder getEncryptedFolder() {
        return encryptedFolder;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public boolean isAutoMount() {
        return autoMount;
    }

    public void setAutoMount(boolean autoMount) {
        this.autoMount = autoMount;
    }
}
