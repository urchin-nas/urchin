package urchin.controller.api.group;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Value.Immutable
@JsonSerialize(as = ImmutableAddUserToGroupDto.class)
@JsonDeserialize(as = ImmutableAddUserToGroupDto.class)
public interface AddUserToGroupDto {

    @NotNull
    @Min(value = 1)
    int getGroupId();

    @NotNull
    @Min(value = 1)
    int getUserId();
}
