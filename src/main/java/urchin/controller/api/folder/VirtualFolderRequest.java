package urchin.controller.api.folder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

@Value.Immutable
@JsonSerialize(as = ImmutableVirtualFolderRequest.class)
@JsonDeserialize(as = ImmutableVirtualFolderRequest.class)
public interface VirtualFolderRequest {

    @Nullable
    @NotNull
    @Size(min = 1)
    Collection<String> getFolders();

    @Nullable
    @NotNull
    @Size(min = 1)
    String getVirtualFolder();
}
