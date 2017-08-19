package urchin.controller.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static urchin.domain.model.Passphrase.ECRYPTFS_MAX_PASSPHRASE_LENGTH;

public class MountEncryptedFolderDto {

    @NotNull
    @Size(min = 1)
    private final String folder;

    @NotNull
    @Size(min = ECRYPTFS_MAX_PASSPHRASE_LENGTH, max = ECRYPTFS_MAX_PASSPHRASE_LENGTH)
    private final String passphrase;

    @JsonCreator
    public MountEncryptedFolderDto(@JsonProperty("folder") String folder, @JsonProperty("passphrase") String passphrase) {
        this.folder = folder;
        this.passphrase = passphrase;
    }

    public String getFolder() {
        return folder;
    }

    public String getPassphrase() {
        return passphrase;
    }
}
