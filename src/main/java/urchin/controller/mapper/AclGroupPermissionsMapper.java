package urchin.controller.mapper;

import urchin.controller.api.permission.AclGroupPermissionsResponse;
import urchin.controller.api.permission.ImmutableAclGroupPermissionsResponse;
import urchin.model.group.Group;
import urchin.model.permission.AclPermission;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static urchin.controller.mapper.AclPermissionMapper.mapToAclPermissionResponse;
import static urchin.controller.mapper.GroupMapper.mapToGroupResponse;

public class AclGroupPermissionsMapper {

    public static List<AclGroupPermissionsResponse> mapToAclGroupPermissionsResponse(Map<Group, AclPermission> groupPermissions) {
        return groupPermissions.entrySet().stream()
                .map(e -> ImmutableAclGroupPermissionsResponse.builder()
                        .group(mapToGroupResponse(e.getKey()))
                        .aclPermissions(mapToAclPermissionResponse(e.getValue()))
                        .build())
                .collect(Collectors.toList());
    }
}
