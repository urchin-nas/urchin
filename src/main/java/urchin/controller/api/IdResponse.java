package urchin.controller.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableIdResponse.class)
@JsonDeserialize(as = ImmutableIdResponse.class)
public interface IdResponse {

    @Value.Parameter
    Integer getId();
}
