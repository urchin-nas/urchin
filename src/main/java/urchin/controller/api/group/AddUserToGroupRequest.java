package urchin.controller.api.group;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import javax.annotation.Nullable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Value.Immutable
@JsonSerialize(as = ImmutableAddUserToGroupRequest.class)
@JsonDeserialize(as = ImmutableAddUserToGroupRequest.class)
public interface AddUserToGroupRequest {

    @Nullable
    @NotNull
    @Min(value = 1)
    Integer getGroupId();

    @Nullable
    @NotNull
    @Min(value = 1)
    Integer getUserId();
}
