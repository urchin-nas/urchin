package urchin.controller.api.folder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableFolderDetailsResponse.class)
@JsonDeserialize(as = ImmutableFolderDetailsResponse.class)
public interface FolderDetailsResponse {

    Integer getFolderId();

    String getFolderName();

    String getFolderPath();
}
