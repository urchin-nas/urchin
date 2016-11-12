package urchin.api;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static urchin.api.support.validation.ValidationConstants.FIELD_EMPTY;
import static urchin.api.support.validation.ValidationConstants.FIELD_MISSING;
import static urchin.domain.model.Passphrase.ECRYPTFS_MAX_PASSPHRASE_LENGTH;

public class MountEncryptedFolderDto {

    @NotNull(message = FIELD_MISSING)
    @Size(min = 1, message = FIELD_EMPTY)
    private String folder;

    @NotNull(message = FIELD_MISSING)
    @Size(min = ECRYPTFS_MAX_PASSPHRASE_LENGTH, max = ECRYPTFS_MAX_PASSPHRASE_LENGTH)
    private String passphrase;

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getPassphrase() {
        return passphrase;
    }

    public void setPassphrase(String passphrase) {
        this.passphrase = passphrase;
    }
}
