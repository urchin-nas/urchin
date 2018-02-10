package urchin.controller.mapper;

import urchin.controller.api.group.GroupResponse;
import urchin.controller.api.group.ImmutableGroupResponse;
import urchin.model.group.Group;

import java.util.List;
import java.util.stream.Collectors;

public class GroupMapper {

    public static List<GroupResponse> mapToGroupsResponses(List<Group> groups) {
        return groups.stream()
                .map(GroupMapper::mapToGroupResponse)
                .collect(Collectors.toList());
    }

    public static GroupResponse mapToGroupResponse(Group group) {
        return ImmutableGroupResponse.builder()
                .groupId(group.getGroupId().getValue())
                .groupName(group.getName().getValue())
                .build();
    }
}
