package urchin.controller.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableIdDto.class)
@JsonDeserialize(as = ImmutableIdDto.class)
public interface IdDto {

    @Value.Parameter
    int getId();
}
