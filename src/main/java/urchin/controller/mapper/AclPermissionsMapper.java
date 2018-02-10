package urchin.controller.mapper;

import urchin.controller.api.permission.AclGroupPermissionsResponse;
import urchin.controller.api.permission.AclResponse;
import urchin.controller.api.permission.AclUserPermissionsResponse;
import urchin.controller.api.permission.ImmutableAclResponse;
import urchin.model.permission.AclPermissions;

import java.util.List;

import static urchin.controller.mapper.AclGroupPermissionsMapper.mapToAclGroupPermissionsResponse;
import static urchin.controller.mapper.AclUserPermissionsMapper.mapToAclUserPermissionsResponse;

public class AclPermissionsMapper {

    public static AclResponse mapToAclResponse(AclPermissions acl) {
        List<AclGroupPermissionsResponse> aclGroupPermissionsResponses = mapToAclGroupPermissionsResponse(acl.getGroups());
        List<AclUserPermissionsResponse> aclUserPermissionsResponses = mapToAclUserPermissionsResponse(acl.getUsers());

        return ImmutableAclResponse.builder()
                .groups(aclGroupPermissionsResponses)
                .users(aclUserPermissionsResponses)
                .build();
    }
}
