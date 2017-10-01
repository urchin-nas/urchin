package urchin.controller.api.mapper;

import urchin.controller.api.group.GroupDto;
import urchin.controller.api.group.ImmutableGroupDto;
import urchin.domain.model.Group;

import java.util.List;
import java.util.stream.Collectors;

public class GroupMapper {

    public static List<GroupDto> mapToGroupsDto(List<Group> groups) {
        return groups.stream()
                .map(GroupMapper::mapToGroupDto)
                .collect(Collectors.toList());
    }

    public static GroupDto mapToGroupDto(Group group) {
        return ImmutableGroupDto.builder()
                .groupId(group.getGroupId().getId())
                .groupName(group.getName())
                .build();
    }
}
