package urchin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import urchin.controller.api.IdResponse;
import urchin.controller.api.ImmutableIdResponse;
import urchin.controller.api.ImmutableMessageResponse;
import urchin.controller.api.MessageResponse;
import urchin.controller.api.group.GroupResponse;
import urchin.controller.api.user.AddUserRequest;
import urchin.controller.api.user.UserResponse;
import urchin.model.user.Password;
import urchin.model.user.User;
import urchin.model.user.UserId;
import urchin.model.user.Username;
import urchin.service.UserService;

import javax.validation.Valid;
import java.util.List;

import static urchin.controller.mapper.GroupMapper.mapToGroupsResponses;
import static urchin.controller.mapper.UserMapper.mapToUserResponse;
import static urchin.controller.mapper.UserMapper.mapToUsersResponses;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<UserResponse> getUsers() {
        List<User> users = userService.getUsers();
        return mapToUsersResponses(users);
    }

    @RequestMapping(value = "{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public UserResponse getUser(@PathVariable int userId) {
        UserId uid = UserId.of(userId);
        return mapToUserResponse(userService.getUser(uid));
    }

    @RequestMapping(value = "add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public IdResponse addUser(@Valid @RequestBody AddUserRequest addUserRequest) {
        UserId userId = userService.addUser(Username.of(addUserRequest.getUsername()), Password.of(addUserRequest.getPassword()));
        return ImmutableIdResponse.of(userId.getValue());
    }

    @RequestMapping(value = "{userId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MessageResponse removeUser(@PathVariable int userId) {
        userService.removeUser(UserId.of(userId));
        return ImmutableMessageResponse.of("User removed");
    }

    @RequestMapping(value = "{userId}/groups", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<GroupResponse> getGroupsForUser(@PathVariable int userId) {
        return mapToGroupsResponses(userService.listGroupsForUser(UserId.of(userId)));
    }

}
