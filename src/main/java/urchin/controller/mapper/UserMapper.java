package urchin.controller.mapper;

import urchin.controller.api.user.ImmutableUserResponse;
import urchin.controller.api.user.UserResponse;
import urchin.model.user.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static List<UserResponse> mapToUsersResponses(List<User> users) {
        return users.stream()
                .map(UserMapper::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public static UserResponse mapToUserResponse(User user) {
        return ImmutableUserResponse.builder()
                .userId(user.getUserId().getValue())
                .username(user.getUsername().getValue())
                .build();
    }
}
