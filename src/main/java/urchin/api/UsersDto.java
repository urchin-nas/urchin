package urchin.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UsersDto {

    private final List<UserDto> users;

    @JsonCreator
    public UsersDto(@JsonProperty("users") List<UserDto> userDtos) {
        this.users = userDtos;
    }

    public List<UserDto> getUsers() {
        return users;
    }

}
