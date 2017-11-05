package urchin.model.folder;

import org.immutables.value.Value;

import java.nio.file.Files;
import java.nio.file.Path;

abstract class FolderWrapper {

    @Value.Parameter
    public abstract Path getPath();

    @Value.Check
    void validatePath() {
        if (!getPath().isAbsolute()) {
            throw new IllegalArgumentException("Path must be absolute, but was " + getPath());
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + getPath().toAbsolutePath() + ")";
    }

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
