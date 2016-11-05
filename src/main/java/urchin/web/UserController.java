package urchin.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import urchin.api.AddUserApi;
import urchin.api.UsersApi;
import urchin.api.support.ResponseMessage;
import urchin.domain.model.User;
import urchin.domain.model.UserId;
import urchin.service.UserService;

import javax.validation.Valid;
import java.util.List;

import static urchin.api.mapper.UserMapper.mapToUser;
import static urchin.api.mapper.UserMapper.mapToUsersApi;
import static urchin.api.support.DataResponseEntityBuilder.createOkResponse;
import static urchin.api.support.DataResponseEntityBuilder.createResponse;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessage<UsersApi>> getUsers() {
        List<User> users = userService.getUsers();
        return createResponse(mapToUsersApi(users));
    }

    @RequestMapping(value = "add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessage<String>> addUser(@Valid @RequestBody AddUserApi addUserApi) {
        userService.addUser(mapToUser(addUserApi), addUserApi.getPassword());
        return createOkResponse();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessage<String>> removeUser(@PathVariable int id) {
        userService.removeUser(new UserId(id));
        return createOkResponse();
    }

}
