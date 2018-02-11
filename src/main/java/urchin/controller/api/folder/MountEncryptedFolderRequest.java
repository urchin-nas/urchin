package urchin.controller.api.folder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import javax.annotation.Nullable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static urchin.model.folder.Passphrase.ECRYPTFS_MAX_PASSPHRASE_LENGTH;

@Value.Immutable
@JsonSerialize(as = ImmutableMountEncryptedFolderRequest.class)
@JsonDeserialize(as = ImmutableMountEncryptedFolderRequest.class)
public interface MountEncryptedFolderRequest {

    @Nullable
    @NotNull
    @Min(1)
    Integer getFolderId();

    @Nullable
    @NotNull
    @Size(min = ECRYPTFS_MAX_PASSPHRASE_LENGTH, max = ECRYPTFS_MAX_PASSPHRASE_LENGTH)
    String getPassphrase();

}
