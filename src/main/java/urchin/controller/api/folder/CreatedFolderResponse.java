package urchin.controller.api.folder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableCreatedFolderResponse.class)
@JsonDeserialize(as = ImmutableCreatedFolderResponse.class)
public interface CreatedFolderResponse {

    Integer getId();

    String getPassphrase();
}
