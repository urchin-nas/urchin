package urchin.api.mapper;

import urchin.api.AddGroupDto;
import urchin.api.GroupDto;
import urchin.api.GroupsDto;
import urchin.domain.model.Group;

import java.util.List;
import java.util.stream.Collectors;

public class GroupMapper {

    public static Group mapToGroup(AddGroupDto addGroupDto) {
        return new Group(addGroupDto.getName());
    }

    public static GroupsDto mapToGroupsApi(List<Group> groups) {
        return new GroupsDto(
                groups.stream()
                        .map(GroupMapper::mapToGroupApi)
                        .collect(Collectors.toList())
        );
    }

    public static GroupDto mapToGroupApi(Group group) {
        return new GroupDto(
                group.getGroupId().getId(),
                group.getName()
        );
    }
}
