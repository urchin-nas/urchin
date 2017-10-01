package urchin.controller.api.folder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value.Immutable
@JsonSerialize(as = ImmutableFolderDto.class)
@JsonDeserialize(as = ImmutableFolderDto.class)
public interface FolderDto {

    @NotNull
    @Size(min = 1)
    @Value.Parameter
    String getFolder();
}
