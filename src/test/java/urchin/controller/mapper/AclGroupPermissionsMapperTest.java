package urchin.controller.mapper;

import org.junit.Test;
import urchin.controller.api.permission.AclGroupPermissionsResponse;
import urchin.controller.api.permission.AclPermissionResponse;
import urchin.model.group.Group;
import urchin.model.group.GroupId;
import urchin.model.group.GroupName;
import urchin.model.group.ImmutableGroup;
import urchin.model.permission.AclPermission;
import urchin.model.permission.ImmutableAclPermission;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AclGroupPermissionsMapperTest {

    @Test
    public void mappedToResponse() {
        Group group = ImmutableGroup.builder()
                .groupId(GroupId.of(1))
                .name(GroupName.of("groupName"))
                .created(LocalDateTime.now())
                .build();

        AclPermission aclPermission = ImmutableAclPermission.builder()
                .permissions("rwx")
                .build();

        Map<Group, AclPermission> groupAclPermissions = Collections.singletonMap(group, aclPermission);

        List<AclGroupPermissionsResponse> aclGroupPermissionsResponses = AclGroupPermissionsMapper.mapToAclGroupPermissionsResponse(groupAclPermissions);

        assertThat(aclGroupPermissionsResponses).hasSize(1);
        AclGroupPermissionsResponse aclGroupPermissionsResponse = aclGroupPermissionsResponses.get(0);
        assertThat(aclGroupPermissionsResponse.getGroup().getGroupId()).isEqualTo(group.getGroupId().getValue());
        AclPermissionResponse aclPermissions = aclGroupPermissionsResponse.getAclPermissions();
        assertThat(aclPermissions.hasRead()).isTrue();
        assertThat(aclPermissions.hasWrite()).isTrue();
        assertThat(aclPermissions.hasExecute()).isTrue();

    }

}