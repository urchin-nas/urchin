package urchin.model.folder;

import org.immutables.value.Value;

import java.io.File;
import java.nio.file.Path;

public abstract class FolderWrapper {

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
        return this.getPath().toFile().exists();
    }

    public boolean isEmpty() {
        File file = this.getPath().toFile();
        if (file != null && file.exists()) {
            return file.list().length == 0;
        } else {
            throw new IllegalArgumentException(String.format("Folder %s does not exist", this));
        }
    }

    public String toAbsolutePath() {
        return getPath().toAbsolutePath().toString();
    }
}
