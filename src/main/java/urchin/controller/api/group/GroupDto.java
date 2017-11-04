package urchin.controller.api.group;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableGroupDto.class)
@JsonDeserialize(as = ImmutableGroupDto.class)
public interface GroupDto {

    Integer getGroupId();

    String getGroupName();

}
