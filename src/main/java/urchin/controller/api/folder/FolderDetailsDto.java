package urchin.controller.api.folder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableFolderDetailsDto.class)
@JsonDeserialize(as = ImmutableFolderDetailsDto.class)
public interface FolderDetailsDto {

    Integer getFolderId();

    String getFolder();
}
