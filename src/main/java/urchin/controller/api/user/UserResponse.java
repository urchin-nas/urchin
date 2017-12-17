package urchin.controller.api.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableUserResponse.class)
@JsonDeserialize(as = ImmutableUserResponse.class)
public interface UserResponse {

    Integer getUserId();

    String getUsername();

}
