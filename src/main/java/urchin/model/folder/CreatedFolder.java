package urchin.model.folder;

import org.immutables.value.Value;

@Value.Immutable
public interface CreatedFolder {

    FolderId getFolderId();

    Passphrase getPassphrase();
}
