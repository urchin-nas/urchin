package urchin.controller.api.folder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

@Value.Immutable
@JsonSerialize(as = ImmutableVirtualFolderDto.class)
@JsonDeserialize(as = ImmutableVirtualFolderDto.class)
public interface VirtualFolderDto {

    @NotNull
    @Size(min = 1)
    Collection<String> getFolders();

    @NotNull
    @Size(min = 1)
    String getVirtualFolder();
}
