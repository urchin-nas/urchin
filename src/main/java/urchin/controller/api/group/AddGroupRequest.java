package urchin.controller.api.group;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value.Immutable
@JsonSerialize(as = ImmutableAddGroupRequest.class)
@JsonDeserialize(as = ImmutableAddGroupRequest.class)
public interface AddGroupRequest {

    @Nullable
    @NotNull
    @Size(min = 3, max = 32)
    @Value.Parameter
    String getGroupName();
}
