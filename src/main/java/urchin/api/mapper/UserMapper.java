package urchin.api.mapper;

import urchin.api.AddUserDto;
import urchin.api.UserDto;
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

    private static UserDto mapToUserDto(User user) {
        return new UserDto(
                user.getUserId().getId(),
                user.getUsername()
        );
    }
}
