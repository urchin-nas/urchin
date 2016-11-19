package urchin.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

import static urchin.api.support.validation.ValidationConstants.FIELD_MISSING;

public class AddUserToGroupDto {

    @NotNull(message = FIELD_MISSING)
    private final int groupId;

    @NotNull(message = FIELD_MISSING)
    private final int userId;

    @JsonCreator
    public AddUserToGroupDto(@JsonProperty("groupId") int groupId, @JsonProperty("userId") int userId) {
        this.groupId = groupId;
        this.userId = userId;
    }

    public int getGroupId() {
        return groupId;
    }

    public int getUserId() {
        return userId;
    }
}
