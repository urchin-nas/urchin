package urchin.controller.api.group;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sun.istack.internal.Nullable;
import org.immutables.value.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Value.Immutable
@JsonSerialize(as = ImmutableAddUserToGroupDto.class)
@JsonDeserialize(as = ImmutableAddUserToGroupDto.class)
public interface AddUserToGroupDto {

    @Nullable
    @NotNull
    @Min(value = 1)
    Integer getGroupId();

    @Nullable
    @NotNull
    @Min(value = 1)
    Integer getUserId();
}
