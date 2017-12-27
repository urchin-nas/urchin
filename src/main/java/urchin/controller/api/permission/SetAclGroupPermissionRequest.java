package urchin.controller.api.permission;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import javax.validation.constraints.Min;

@Value.Immutable
@JsonSerialize(as = ImmutableSetAclGroupPermissionRequest.class)
@JsonDeserialize(as = ImmutableSetAclGroupPermissionRequest.class)
public interface SetAclGroupPermissionRequest {

    @Min(value = 1)
    Integer getFolderId();

    @Min(value = 1)
    Integer getGroupId();

    boolean read();

    boolean write();

    boolean execute();
}
