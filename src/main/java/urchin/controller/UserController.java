package urchin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import urchin.controller.api.IdDto;
import urchin.controller.api.ImmutableIdDto;
import urchin.controller.api.ImmutableMessageDto;
import urchin.controller.api.MessageDto;
import urchin.controller.api.group.GroupDto;
import urchin.controller.api.user.AddUserDto;
import urchin.controller.api.user.UserDto;
import urchin.domain.model.ImmutableUserId;
import urchin.domain.model.User;
import urchin.domain.model.UserId;
import urchin.service.UserService;

import javax.validation.Valid;
import java.util.List;

import static urchin.controller.api.mapper.GroupMapper.mapToGroupsDto;
import static urchin.controller.api.mapper.UserMapper.mapToUserDto;
import static urchin.controller.api.mapper.UserMapper.mapToUsersDto;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<UserDto> getUsers() {
        List<User> users = userService.getUsers();
        return mapToUsersDto(users);
    }

    @RequestMapping(value = "{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public UserDto getUser(@PathVariable int userId) {
        UserId uid = ImmutableUserId.of(userId);
        return mapToUserDto(userService.getUser(uid));
    }

    @RequestMapping(value = "add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public IdDto addUser(@Valid @RequestBody AddUserDto addUserDto) {
        UserId userId = userService.addUser(addUserDto.getUsername(), addUserDto.getPassword());
        return ImmutableIdDto.of(userId.getValue());
    }

    @RequestMapping(value = "{userId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MessageDto removeUser(@PathVariable int userId) {
        userService.removeUser(ImmutableUserId.of(userId));
        return ImmutableMessageDto.of("User removed");
    }

    @RequestMapping(value = "{userId}/groups", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<GroupDto> getGroupsForUser(@PathVariable int userId) {
        return mapToGroupsDto(userService.listGroupsForUser(ImmutableUserId.of(userId)));
    }

}
