package urchin.controller.api.permission;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;
import urchin.controller.api.user.UserResponse;

@Value.Immutable
@Value.Style
@JsonSerialize(as = ImmutableAclUserPermissionsResponse.class)
@JsonDeserialize(as = ImmutableAclUserPermissionsResponse.class)
public interface AclUserPermissionsResponse {

    UserResponse getUser();

    AclPermissionResponse getAclPermissions();
}
