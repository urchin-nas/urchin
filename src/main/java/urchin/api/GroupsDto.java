package urchin.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GroupsDto {

    private final List<GroupDto> groups;

    @JsonCreator
    public GroupsDto(@JsonProperty("groups") List<GroupDto> groupDtos) {
        this.groups = groupDtos;
    }

    public List<GroupDto> getGroups() {
        return groups;
    }

}
