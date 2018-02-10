package urchin.controller.mapper;

import org.junit.Test;
import urchin.controller.api.permission.AclPermissionResponse;
import urchin.model.permission.AclPermission;
import urchin.model.permission.ImmutableAclPermission;

import static org.assertj.core.api.Assertions.assertThat;

public class AclPermissionMapperTest {

    @Test
    public void mappedToResponse() {
        AclPermission aclPermission = ImmutableAclPermission.builder()
                .permissions("rwx")
                .build();

        AclPermissionResponse aclPermissionResponse = AclPermissionMapper.mapToAclPermissionResponse(aclPermission);

        assertThat(aclPermissionResponse.hasRead()).isTrue();
        assertThat(aclPermissionResponse.hasWrite()).isTrue();
        assertThat(aclPermissionResponse.hasExecute()).isTrue();
    }

}