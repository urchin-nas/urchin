package urchin.controller.api.mapper;

import urchin.controller.api.permission.AclPermissionResponse;
import urchin.controller.api.permission.ImmutableAclPermissionResponse;
import urchin.model.permission.AclPermission;

public class AclPermissionMapper {

    public static AclPermissionResponse mapToAclPermissionResponse(AclPermission aclPermission) {
        return ImmutableAclPermissionResponse.builder()
                .hasRead(aclPermission.getPermissions().contains("r"))
                .hasWrite(aclPermission.getPermissions().contains("w"))
                .hasExecute(aclPermission.getPermissions().contains("x"))
                .build();
    }
}
