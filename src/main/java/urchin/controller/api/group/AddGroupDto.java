package urchin.controller.api.group;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AddGroupDto {

    @NotNull
    @Size(min = 3, max = 32)
    private final String groupName;

    @JsonCreator
    public AddGroupDto(@JsonProperty("groupName") String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }
}
