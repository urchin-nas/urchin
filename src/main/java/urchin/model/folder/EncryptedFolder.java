package urchin.model.folder;

import org.immutables.value.Value;
import urchin.util.EncryptedFolderUtil;

@Value.Immutable
public abstract class EncryptedFolder extends FolderWrapper {

    public Folder toRegularFolder() {
        return EncryptedFolderUtil.getFolder(this);
    }

}
