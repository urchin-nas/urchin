package urchin.domain.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

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

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
