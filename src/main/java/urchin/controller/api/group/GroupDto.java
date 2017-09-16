package urchin.controller.api.group;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GroupDto {

    private final int groupId;
    private final String groupName;

    @JsonCreator
    public GroupDto(@JsonProperty("groupId") int groupId, @JsonProperty("groupName") String groupName) {
        this.groupId = groupId;
        this.groupName = groupName;
    }

    public int getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

}
