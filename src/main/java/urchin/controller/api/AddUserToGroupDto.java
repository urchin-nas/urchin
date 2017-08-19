package urchin.controller.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class AddUserToGroupDto {

    @NotNull
    @Min(value = 1)
    private final int groupId;

    @NotNull
    @Min(value = 1)
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
