package urchin.controller.api.folder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sun.istack.internal.Nullable;
import org.immutables.value.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

@Value.Immutable
@JsonSerialize(as = ImmutableVirtualFolderDto.class)
@JsonDeserialize(as = ImmutableVirtualFolderDto.class)
public interface VirtualFolderDto {

    @Nullable
    @NotNull
    @Size(min = 1)
    Collection<String> getFolders();

    @Nullable
    @NotNull
    @Size(min = 1)
    String getVirtualFolder();
}
