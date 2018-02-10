package urchin.controller.mapper;

import org.junit.Test;
import urchin.controller.api.permission.AclPermissionResponse;
import urchin.controller.api.permission.AclUserPermissionsResponse;
import urchin.model.permission.AclPermission;
import urchin.model.permission.ImmutableAclPermission;
import urchin.model.user.ImmutableUser;
import urchin.model.user.User;
import urchin.model.user.UserId;
import urchin.model.user.Username;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AclUserPermissionsMapperTest {

    @Test
    public void mappedToResponse() {
        User user = ImmutableUser.builder()
                .userId(UserId.of(2))
                .username(Username.of("username"))
                .created(LocalDateTime.now())
                .build();

        AclPermission aclPermission = ImmutableAclPermission.builder()
                .permissions("rwx")
                .build();

        Map<User, AclPermission> userAclPermissions = Collections.singletonMap(user, aclPermission);

        List<AclUserPermissionsResponse> aclUserPermissionsResponses = AclUserPermissionsMapper.mapToAclUserPermissionsResponse(userAclPermissions);

        assertThat(aclUserPermissionsResponses).hasSize(1);
        AclUserPermissionsResponse aclUserPermissionsResponse = aclUserPermissionsResponses.get(0);
        assertThat(aclUserPermissionsResponse.getUser().getUserId()).isEqualTo(user.getUserId().getValue());
        AclPermissionResponse aclPermissions = aclUserPermissionsResponse.getAclPermissions();
        assertThat(aclPermissions.hasRead()).isTrue();
        assertThat(aclPermissions.hasWrite()).isTrue();
        assertThat(aclPermissions.hasExecute()).isTrue();
    }

}