package urchin.controller.api.permission;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import javax.validation.constraints.Min;

@Value.Immutable
@JsonSerialize(as = ImmutableSetAclUserPermissionRequest.class)
@JsonDeserialize(as = ImmutableSetAclUserPermissionRequest.class)
public interface SetAclUserPermissionRequest {

    @Min(value = 1)
    Integer getFolderId();

    @Min(value = 1)
    Integer getUserId();

    boolean read();

    boolean write();

    boolean execute();
}
