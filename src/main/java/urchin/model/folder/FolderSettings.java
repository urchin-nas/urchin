package urchin.model.folder;

import org.immutables.value.Value;

import java.nio.file.Path;
import java.time.LocalDateTime;

@Value.Immutable
public interface FolderSettings {

    FolderId getFolderId();

    Path getFolder();

    EncryptedFolder getEncryptedFolder();

    LocalDateTime getCreated();

    boolean isAutoMount();

}
