package urchin.web;

import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import urchin.api.AddUserDto;
import urchin.api.UserDto;
import urchin.api.UsersDto;
import urchin.api.support.ResponseMessage;
import urchin.testutil.TestApplication;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class UserControllerIT extends TestApplication {

    private static final String USERNAME_PREFIX = "urchin_";
    private static final String PASSWORD = "superSecret";

    @Test
    public void addAndRemoveUser() {
        AddUserDto addUserDto = new AddUserDto(USERNAME_PREFIX + System.currentTimeMillis(), PASSWORD);

        ResponseEntity<ResponseMessage<String>> addUserResponse = addUserRequest(addUserDto);

        assertEquals(HttpStatus.OK, addUserResponse.getStatusCode());

        ResponseEntity<ResponseMessage<UsersDto>> usersResponse = getUsersRequest();

        assertEquals(HttpStatus.OK, usersResponse.getStatusCode());
        List<UserDto> users = usersResponse.getBody().getData().getUsers();
        assertFalse(users.isEmpty());
        List<UserDto> userDtos = users.stream()
                .filter(userDto -> userDto.getUsername().equals(addUserDto.getUsername()))
                .collect(Collectors.toList());
        assertEquals(1, userDtos.size());
        assertEquals(addUserDto.getUsername(), userDtos.get(0).getUsername());

        ResponseEntity<ResponseMessage<String>> removeUserResponse = removeUserRequest(userDtos.get(0).getUserId());

        assertEquals(HttpStatus.OK, removeUserResponse.getStatusCode());
    }

    private ResponseEntity<ResponseMessage<String>> addUserRequest(AddUserDto addUserDto) {
        return testRestTemplate.exchange(discoverControllerPath() + "/add", HttpMethod.POST, new HttpEntity<>(addUserDto), new ParameterizedTypeReference<ResponseMessage<String>>() {
        });
    }

    private ResponseEntity<ResponseMessage<UsersDto>> getUsersRequest() {
        return testRestTemplate.exchange(discoverControllerPath(), HttpMethod.GET, null, new ParameterizedTypeReference<ResponseMessage<UsersDto>>() {
        });
    }

    private ResponseEntity<ResponseMessage<String>> removeUserRequest(int userId) {
        return testRestTemplate.exchange(discoverControllerPath() + "/" + userId, HttpMethod.DELETE, null, new ParameterizedTypeReference<ResponseMessage<String>>() {
        });
    }
}