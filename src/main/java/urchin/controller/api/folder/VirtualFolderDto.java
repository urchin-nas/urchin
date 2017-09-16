package urchin.controller.api.folder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class VirtualFolderDto {

    @NotNull
    @Size(min = 1)
    private final List<String> folders;

    @NotNull
    @Size(min = 1)
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
