package urchin.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static urchin.api.support.validation.ValidationConstants.FIELD_EMPTY;
import static urchin.api.support.validation.ValidationConstants.FIELD_MISSING;

public class UnmountVirtualFolderDto {

    @NotNull(message = FIELD_MISSING)
    @Size(min = 1, message = FIELD_EMPTY)
    private final String folder;

    @JsonCreator
    public UnmountVirtualFolderDto(@JsonProperty("folder") String folder) {
        this.folder = folder;
    }

    public String getFolder() {
        return folder;
    }
}
