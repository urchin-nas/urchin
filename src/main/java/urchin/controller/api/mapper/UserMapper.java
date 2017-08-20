package urchin.controller.api.mapper;

import urchin.controller.api.AddUserDto;
import urchin.controller.api.UserDto;
import urchin.domain.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static User mapToUser(AddUserDto addUserDto) {
        return new User(addUserDto.getUsername());
    }

    public static List<UserDto> mapToUsersDto(List<User> users) {
        return users.stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    public static UserDto mapToUserDto(User user) {
        return new UserDto(
                user.getUserId().getId(),
                user.getUsername()
        );
    }
}
