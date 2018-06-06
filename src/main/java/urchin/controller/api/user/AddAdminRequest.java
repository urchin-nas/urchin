package urchin.controller.api.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value.Immutable
@JsonSerialize(as = ImmutableAddAdminRequest.class)
@JsonDeserialize(as = ImmutableAddAdminRequest.class)
public interface AddAdminRequest {

    @Nullable
    @NotNull
    @Size(min = 3, max = 32)
    String getUsername();
}
