package urchin.controller.mapper;

import urchin.controller.api.permission.AclPermissionResponse;
import urchin.controller.api.permission.ImmutableAclPermissionResponse;
import urchin.model.permission.AclPermission;

import static urchin.model.permission.AclPermission.*;

public class AclPermissionMapper {

    public static AclPermissionResponse mapToAclPermissionResponse(AclPermission aclPermission) {
        return ImmutableAclPermissionResponse.builder()
                .hasRead(aclPermission.getPermissions().contains(READ))
                .hasWrite(aclPermission.getPermissions().contains(WRITE))
                .hasExecute(aclPermission.getPermissions().contains(EXECUTE))
                .build();
    }
}
