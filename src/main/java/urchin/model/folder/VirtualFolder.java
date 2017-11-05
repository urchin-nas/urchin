package urchin.model.folder;

import org.immutables.value.Value;

import java.nio.file.Files;
import java.nio.file.Path;

@Value.Immutable
public abstract class VirtualFolder {

    @Value.Parameter
    public abstract Path getPath();

    public boolean isExisting() {
        return Files.exists(this.getPath());
    }

    public boolean isEmpty() {
        return this.getPath().toFile().list().length == 0;
    }

    public String toAbsolutePath() {
        return getPath().toAbsolutePath().toString();
    }

}
