package urchin.controller.api.folder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import javax.annotation.Nullable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Value.Immutable
@JsonSerialize(as = ImmutableUnmountEncryptedFolderRequest.class)
@JsonDeserialize(as = ImmutableUnmountEncryptedFolderRequest.class)
public interface UnmountEncryptedFolderRequest {

    @Nullable
    @NotNull
    @Min(1)
    Integer getFolderId();
}
