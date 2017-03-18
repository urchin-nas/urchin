package urchin.controller.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import static urchin.controller.api.support.validation.ValidationConstants.FIELD_MISSING;
import static urchin.controller.api.support.validation.ValidationConstants.FIELD_SMALL_VALUE;

public class AddUserToGroupDto {

    @NotNull(message = FIELD_MISSING)
    @Min(value = 1, message = FIELD_SMALL_VALUE)
    private final int groupId;

    @NotNull(message = FIELD_MISSING)
    @Min(value = 1, message = FIELD_SMALL_VALUE)
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
