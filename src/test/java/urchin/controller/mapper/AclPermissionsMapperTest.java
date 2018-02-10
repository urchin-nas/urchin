package urchin.controller.mapper;

import org.junit.Test;
import urchin.controller.api.permission.AclResponse;
import urchin.model.group.Group;
import urchin.model.group.GroupId;
import urchin.model.group.GroupName;
import urchin.model.group.ImmutableGroup;
import urchin.model.permission.AclPermission;
import urchin.model.permission.AclPermissions;
import urchin.model.permission.ImmutableAclPermission;
import urchin.model.permission.ImmutableAclPermissions;
import urchin.model.user.ImmutableUser;
import urchin.model.user.User;
import urchin.model.user.UserId;
import urchin.model.user.Username;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class AclPermissionsMapperTest {

    @Test
    public void mappedToResponse() {
        Group group = ImmutableGroup.builder()
                .groupId(GroupId.of(1))
                .name(GroupName.of("groupName"))
                .created(LocalDateTime.now())
                .build();

        User user = ImmutableUser.builder()
                .userId(UserId.of(2))
                .username(Username.of("username"))
                .created(LocalDateTime.now())
                .build();

        AclPermission aclPermission = ImmutableAclPermission.builder()
                .permissions("rwx")
                .build();

        AclPermissions aclPermissions = ImmutableAclPermissions.builder()
                .putGroups(group, aclPermission)
                .putUsers(user, aclPermission)
                .build();

        AclResponse aclResponse = AclPermissionsMapper.mapToAclResponse(aclPermissions);

        assertThat(aclResponse.getUsers()).hasSize(1);
        assertThat(aclResponse.getGroups()).hasSize(1);
    }

}