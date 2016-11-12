package urchin.api.mapper;

import urchin.api.AddUserDto;
import urchin.api.UserDto;
import urchin.api.UsersDto;
import urchin.domain.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static User mapToUser(AddUserDto addUserDto) {
        return new User(addUserDto.getUsername());
    }

    public static UsersDto mapToUsersApi(List<User> users) {
        return new UsersDto(
                users.stream()
                        .map(UserMapper::mapToUserApi)
                        .collect(Collectors.toList())
        );
    }

    public static UserDto mapToUserApi(User user) {
        return new UserDto(
                user.getUserId().getId(),
                user.getUsername()
        );
    }
}
