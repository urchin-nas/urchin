package urchin.model;

import org.immutables.value.Value;

import java.nio.file.Path;
import java.time.LocalDateTime;

@Value.Immutable
public interface FolderSettings {

    int getId();

    Path getFolder();

    EncryptedFolder getEncryptedFolder();

    LocalDateTime getCreated();

    boolean isAutoMount();

}
