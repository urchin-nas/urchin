package urchin.controller.api.folder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutablePassphraseDto.class)
@JsonDeserialize(as = ImmutablePassphraseDto.class)
public interface PassphraseDto {

    @Value.Parameter
    String getPassphrase();
}
