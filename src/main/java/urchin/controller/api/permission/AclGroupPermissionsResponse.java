package urchin.controller.api.permission;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;
import urchin.controller.api.group.GroupResponse;

@Value.Immutable
@Value.Style
@JsonSerialize(as = ImmutableAclGroupPermissionsResponse.class)
@JsonDeserialize(as = ImmutableAclGroupPermissionsResponse.class)
public interface AclGroupPermissionsResponse {

    GroupResponse getGroup();

    AclPermissionResponse getAclPermissions();
}
