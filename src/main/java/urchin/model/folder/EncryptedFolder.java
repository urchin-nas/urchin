package urchin.model.folder;

import org.immutables.value.Value;

import java.nio.file.Path;

@Value.Immutable
public interface EncryptedFolder {

    @Value.Parameter
    Path getPath();

}
