package urchin.controller.api.permission;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.Map;

@Value.Immutable
@JsonSerialize(as = ImmutableAclResponse.class)
@JsonDeserialize(as = ImmutableAclResponse.class)
public interface AclResponse {

    Map<String, AclPermissionResponse> getGroups();

    Map<String, AclPermissionResponse> getUsers();
}
