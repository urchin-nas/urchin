package urchin.api;

import java.util.List;

public class GroupsDto {

    private List<GroupDto> groups;

    private GroupsDto() {
    }

    public GroupsDto(List<GroupDto> groupDtos) {
        this.groups = groupDtos;
    }

    public List<GroupDto> getGroups() {
        return groups;
    }

}
