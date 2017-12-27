package urchin.controller.api.mapper;

import urchin.controller.api.permission.AclPermissionResponse;
import urchin.controller.api.permission.AclResponse;
import urchin.controller.api.permission.ImmutableAclResponse;
import urchin.model.permission.Acl;

import static urchin.controller.api.mapper.AclPermissionMapper.mapToAclPermissionResponse;

public class AclMapper {

    public static AclResponse mapToAclResponse(Acl acl) {
        ImmutableAclResponse.Builder builder = ImmutableAclResponse.builder();

        acl.getGroups().forEach((groupName, aclPermission) -> {
            AclPermissionResponse aclPermissionResponse = mapToAclPermissionResponse(aclPermission);
            builder.putGroups(groupName.getValue(), aclPermissionResponse);
        });

        acl.getUsers().forEach((username, aclPermission) -> {
            AclPermissionResponse aclPermissionResponse = mapToAclPermissionResponse(aclPermission);
            builder.putUsers(username.getValue(), aclPermissionResponse);
        });

        return builder.build();
    }
}
