package urchin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import urchin.controller.api.IdDto;
import urchin.controller.api.MessageDto;
import urchin.controller.api.group.AddUserToGroupDto;
import urchin.controller.api.group.GroupDto;
import urchin.controller.api.user.AddUserDto;
import urchin.controller.api.user.UserDto;
import urchin.domain.model.GroupId;
import urchin.domain.model.User;
import urchin.domain.model.UserId;
import urchin.service.UserService;

import javax.validation.Valid;
import java.util.List;

import static urchin.controller.api.mapper.GroupMapper.mapToGroupsDto;
import static urchin.controller.api.mapper.UserMapper.*;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDto> getUsers() {
        List<User> users = userService.getUsers();
        return mapToUsersDto(users);
    }

    @RequestMapping(value = "{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto getUser(@PathVariable int userId) {
        UserId uid = new UserId(userId);
        return mapToUserDto(userService.getUser(uid)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Invalid UserId: %s", uid)))
        );
    }

    @RequestMapping(value = "add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public IdDto addUser(@Valid @RequestBody AddUserDto addUserDto) {
        UserId userId = userService.addUser(mapToUser(addUserDto), addUserDto.getPassword());
        return new IdDto(userId.getId());
    }

    @RequestMapping(value = "{userId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public MessageDto removeUser(@PathVariable int userId) {
        userService.removeUser(new UserId(userId));
        return new MessageDto("User removed");
    }

    @RequestMapping(value = "{userId}/groups", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GroupDto> getGroupsForUser(@PathVariable int userId) {
        return mapToGroupsDto(userService.listGroupsForUser(new UserId(userId)));
    }

}
