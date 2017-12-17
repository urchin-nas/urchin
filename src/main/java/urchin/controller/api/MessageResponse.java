package urchin.controller.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableMessageResponse.class)
@JsonDeserialize(as = ImmutableMessageResponse.class)
public interface MessageResponse {

    @Value.Parameter
    String getMessage();
}
