package urchin.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static urchin.api.support.validation.ValidationConstants.FIELD_MISSING;

public class AddGroupApi {

    @NotNull(message = FIELD_MISSING)
    @Size(min = 3, max = 32)
    private final String name;

    @JsonCreator
    public AddGroupApi(@JsonProperty("name") String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
