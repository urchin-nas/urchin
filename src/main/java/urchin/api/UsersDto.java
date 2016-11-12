package urchin.api;

import java.util.List;

public class UsersDto {

    private List<UserDto> users;

    private UsersDto() {
    }

    public UsersDto(List<UserDto> userDtos) {
        this.users = userDtos;
    }

    public List<UserDto> getUsers() {
        return users;
    }

}
