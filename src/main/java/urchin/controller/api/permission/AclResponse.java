package urchin.controller.api.permission;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@Value.Style(jdkOnly = true)
@JsonSerialize(as = ImmutableAclResponse.class)
@JsonDeserialize(as = ImmutableAclResponse.class)
public interface AclResponse {

    List<AclGroupPermissionsResponse> getGroups();

    List<AclUserPermissionsResponse> getUsers();
}
