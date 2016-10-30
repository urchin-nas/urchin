package urchin.api.mapper;

import urchin.api.AddGroupApi;
import urchin.api.GroupApi;
import urchin.api.GroupsApi;
import urchin.domain.model.Group;

import java.util.List;
import java.util.stream.Collectors;

public class GroupMapper {

    public static Group mapToGroup(AddGroupApi addGroupApi) {
        return new Group(addGroupApi.getName());
    }

    public static GroupsApi mapToGroupsApi(List<Group> groups) {
        return new GroupsApi(
                groups.stream()
                        .map(GroupMapper::mapToGroupApi)
                        .collect(Collectors.toList())
        );
    }

    public static GroupApi mapToGroupApi(Group group) {
        return new GroupApi(
                group.getGroupId().getId(),
                group.getName()
        );
    }
}
