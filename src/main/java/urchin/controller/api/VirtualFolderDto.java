package urchin.controller.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

import static urchin.controller.api.support.validation.ValidationConstants.FIELD_EMPTY;
import static urchin.controller.api.support.validation.ValidationConstants.FIELD_MISSING;

public class VirtualFolderDto {

    @NotNull(message = FIELD_MISSING)
    @Size(min = 1, message = FIELD_EMPTY)
    private final List<String> folders;

    @NotNull(message = FIELD_MISSING)
    @Size(min = 1, message = FIELD_EMPTY)
    private final String virtualFolder;

    @JsonCreator
    public VirtualFolderDto(@JsonProperty("folders") List<String> folders, @JsonProperty("virtualFolder") String virtualFolder) {
        this.folders = folders;
        this.virtualFolder = virtualFolder;
    }

    public List<String> getFolders() {
        return folders;
    }

    public String getVirtualFolder() {
        return virtualFolder;
    }
}
