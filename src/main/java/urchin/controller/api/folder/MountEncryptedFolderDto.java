package urchin.controller.api.folder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static urchin.model.Passphrase.ECRYPTFS_MAX_PASSPHRASE_LENGTH;

@Value.Immutable
@JsonSerialize(as = ImmutableMountEncryptedFolderDto.class)
@JsonDeserialize(as = ImmutableMountEncryptedFolderDto.class)
public interface MountEncryptedFolderDto {

    @NotNull
    @Size(min = 1)
    String getFolder();

    @NotNull
    @Size(min = ECRYPTFS_MAX_PASSPHRASE_LENGTH, max = ECRYPTFS_MAX_PASSPHRASE_LENGTH)
    String getPassphrase();

}
