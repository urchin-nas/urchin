package urchin.controller.api.folder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;
import urchin.exception.FieldErrorException;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.nio.file.Paths;

@Value.Immutable
@JsonSerialize(as = ImmutableFolderRequest.class)
@JsonDeserialize(as = ImmutableFolderRequest.class)
public abstract class FolderRequest {

    @Nullable
    @NotNull
    @Size(min = 1)
    @Value.Parameter
    public abstract String getFolder();

    @Value.Check
    void validate() {
        if (getFolder() != null) {
            if (getFolder().startsWith("/home/")) {
                throw new FieldErrorException("folder", "Folder path must not start with /home/");
            }
            if (!Paths.get(getFolder()).isAbsolute()) {
                throw new FieldErrorException("folder", "Folder path must be absolute");
            }
        }
    }

}
