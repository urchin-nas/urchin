package urchin.controller.api.permission;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableAclPermissionResponse.class)
@JsonDeserialize(as = ImmutableAclPermissionResponse.class)
public interface AclPermissionResponse {

    boolean hasRead();

    boolean hasWrite();

    boolean hasExecute();

}
