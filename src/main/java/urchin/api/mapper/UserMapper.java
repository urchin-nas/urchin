package urchin.api.mapper;

import urchin.api.AddUserApi;
import urchin.api.UserApi;
import urchin.api.UsersApi;
import urchin.domain.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static User mapToUser(AddUserApi addUserApi) {
        return new User(addUserApi.getUsername());
    }

    public static UsersApi mapToUsersApi(List<User> users) {
        return new UsersApi(
                users.stream()
                        .map(UserMapper::mapToUserApi)
                        .collect(Collectors.toList())
        );
    }

    public static UserApi mapToUserApi(User user) {
        return new UserApi(
                user.getUserId().getId(),
                user.getUsername()
        );
    }
}
