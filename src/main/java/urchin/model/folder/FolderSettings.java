package urchin.model.folder;

import org.immutables.value.Value;

import java.time.LocalDateTime;

@Value.Immutable
public interface FolderSettings {

    FolderId getFolderId();

    Folder getFolder();

    EncryptedFolder getEncryptedFolder();

    LocalDateTime getCreated();

    boolean isAutoMount();

}
