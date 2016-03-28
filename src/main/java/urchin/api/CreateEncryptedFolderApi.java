package urchin.api;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static urchin.api.support.validation.ValidationConstants.FIELD_EMPTY;
import static urchin.api.support.validation.ValidationConstants.FIELD_MISSING;

public class CreateEncryptedFolderApi {

    @NotNull(message = FIELD_MISSING)
    @Size(min = 1, message = FIELD_EMPTY)
    private String folderPath;

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }
}
