package urchin.model.folder;

import org.immutables.value.Value;
import urchin.util.EncryptedFolderUtil;

import java.nio.file.Path;

@Value.Immutable
public abstract class Folder {

    @Value.Parameter
    public abstract Path getPath();

    public String toAbsolutePath() {
        return getPath().toAbsolutePath().toString();
    }

    public EncryptedFolder toEncryptedFolder() {
        return EncryptedFolderUtil.getEncryptedFolder(this.getPath());
    }

}
