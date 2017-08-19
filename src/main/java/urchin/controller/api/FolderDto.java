package urchin.controller.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class FolderDto {

    @NotNull
    @Size(min = 1)
    private final String folder;

    @JsonCreator
    public FolderDto(@JsonProperty("folder") String folder) {
        this.folder = folder;
    }

    public String getFolder() {
        return folder;
    }
}
