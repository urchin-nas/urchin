package urchin.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static urchin.api.support.validation.ValidationConstants.FIELD_MISSING;

public class AddUserDto {

    @NotNull(message = FIELD_MISSING)
    @Size(min = 3, max = 32)
    private final String username;
    @NotNull(message = FIELD_MISSING)
    @Size(min = 6)
    private final String password;

    @JsonCreator
    public AddUserDto(@JsonProperty("username") String username, @JsonProperty("password") String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
