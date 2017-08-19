package urchin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import urchin.controller.api.AddUserDto;
import urchin.controller.api.IdDto;
import urchin.controller.api.MessageDto;
import urchin.controller.api.UserDto;
import urchin.domain.model.User;
import urchin.domain.model.UserId;
import urchin.service.UserService;

import javax.validation.Valid;
import java.util.List;

import static urchin.controller.api.mapper.UserMapper.mapToUser;
import static urchin.controller.api.mapper.UserMapper.mapToUsersDto;

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

}
