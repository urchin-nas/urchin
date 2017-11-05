package urchin.model.folder;

import org.immutables.value.Value;
import urchin.util.EncryptedFolderUtil;

import java.nio.file.Files;
import java.nio.file.Path;

@Value.Immutable
public abstract class EncryptedFolder {

    @Value.Parameter
    public abstract Path getPath();

    public boolean isExisting() {
        return Files.exists(this.getPath());
    }

    public String toAbsolutePath() {
        return getPath().toAbsolutePath().toString();
    }

    public Folder toRegularFolder() {
        return EncryptedFolderUtil.getFolder(this);
    }

}
