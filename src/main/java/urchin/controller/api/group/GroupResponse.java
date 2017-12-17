package urchin.controller.api.group;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableGroupResponse.class)
@JsonDeserialize(as = ImmutableGroupResponse.class)
public interface GroupResponse {

    Integer getGroupId();

    String getGroupName();

}
