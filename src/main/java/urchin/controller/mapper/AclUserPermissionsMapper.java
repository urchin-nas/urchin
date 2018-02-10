package urchin.controller.mapper;

import urchin.controller.api.permission.AclUserPermissionsResponse;
import urchin.controller.api.permission.ImmutableAclUserPermissionsResponse;
import urchin.model.permission.AclPermission;
import urchin.model.user.User;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static urchin.controller.mapper.AclPermissionMapper.mapToAclPermissionResponse;
import static urchin.controller.mapper.UserMapper.mapToUserResponse;

public class AclUserPermissionsMapper {

    public static List<AclUserPermissionsResponse> mapToAclUserPermissionsResponse(Map<User, AclPermission> userPermissions) {
        return userPermissions.entrySet().stream()
                .map(e -> ImmutableAclUserPermissionsResponse.builder()
                        .user(mapToUserResponse(e.getKey()))
                        .aclPermissions(mapToAclPermissionResponse(e.getValue()))
                        .build())
                .collect(Collectors.toList());
    }
}
