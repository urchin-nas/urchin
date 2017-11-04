package urchin.model.folder;

import org.immutables.value.Value;
import urchin.util.EncryptedFolderUtil;

import java.nio.file.Path;

@Value.Immutable
public abstract class EncryptedFolder {

    @Value.Parameter
    public abstract Path getPath();

    public Path toRegularFolder() {
        return EncryptedFolderUtil.getFolder(this);
    }

}
