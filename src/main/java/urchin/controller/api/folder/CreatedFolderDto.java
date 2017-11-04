package urchin.controller.api.folder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableCreatedFolderDto.class)
@JsonDeserialize(as = ImmutableCreatedFolderDto.class)
public interface CreatedFolderDto {

    Integer getId();

    String getPassphrase();
}
