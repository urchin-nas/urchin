package urchin.controller.api.mapper;

import urchin.controller.api.group.AddGroupDto;
import urchin.controller.api.group.GroupDto;
import urchin.domain.model.Group;

import java.util.List;
import java.util.stream.Collectors;

public class GroupMapper {

    public static Group mapToGroup(AddGroupDto addGroupDto) {
        return new Group(addGroupDto.getGroupName());
    }

    public static List<GroupDto> mapToGroupsDto(List<Group> groups) {
        return groups.stream()
                .map(GroupMapper::mapToGroupDto)
                .collect(Collectors.toList());
    }

    public static GroupDto mapToGroupDto(Group group) {
        return new GroupDto(
                group.getGroupId().getId(),
                group.getName()
        );
    }
}
