package urchin.controller.api.mapper;

import urchin.controller.api.user.ImmutableUserDto;
import urchin.controller.api.user.UserDto;
import urchin.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static List<UserDto> mapToUsersDto(List<User> users) {
        return users.stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    public static UserDto mapToUserDto(User user) {
        return ImmutableUserDto.builder()
                .userId(user.getUserId().getValue())
                .username(user.getUsername())
                .build();
    }
}
