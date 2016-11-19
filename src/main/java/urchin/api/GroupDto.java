package urchin.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GroupDto {

    private final int groupId;

    private final String name;

    @JsonCreator
    public GroupDto(@JsonProperty("groupId") int groupId, @JsonProperty("name") String name) {
        this.groupId = groupId;
        this.name = name;
    }

    public int getGroupId() {
        return groupId;
    }

    public String getName() {
        return name;
    }

}
