package urchin.controller.api.group;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value.Immutable
@JsonSerialize(as = ImmutableAddGroupDto.class)
@JsonDeserialize(as = ImmutableAddGroupDto.class)
public interface AddGroupDto {

    @NotNull
    @Size(min = 3, max = 32)
    @Value.Parameter
    String getGroupName();
}
