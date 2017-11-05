package urchin.model.folder;

import org.immutables.value.Value;
import urchin.util.EncryptedFolderUtil;

@Value.Immutable
public abstract class Folder extends FolderWrapper {

    public EncryptedFolder toEncryptedFolder() {
        return EncryptedFolderUtil.getEncryptedFolder(this);
    }

}
