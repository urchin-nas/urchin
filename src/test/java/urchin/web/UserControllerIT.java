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

import static org.junit.Assert.*;
import static urchin.testutil.UnixUserAndGroupCleanup.USERNAME_PREFIX;

public class UserControllerIT extends TestApplication {

    static final String PASSWORD = "superSecret";

    @Test
    public void addAndRemoveUser() {
        AddUserDto addUserDto = new AddUserDto(USERNAME_PREFIX + System.currentTimeMillis(), PASSWORD);

        ResponseEntity<ResponseMessage<Integer>> addUserResponse = addUserRequest(addUserDto);

        assertEquals(HttpStatus.OK, addUserResponse.getStatusCode());
        assertTrue(addUserResponse.getBody().getData() > 0);

        ResponseEntity<ResponseMessage<UsersDto>> usersResponse = getUsersRequest();

        assertEquals(HttpStatus.OK, usersResponse.getStatusCode());
        List<UserDto> users = usersResponse.getBody().getData().getUsers();
        assertFalse(users.isEmpty());
        List<UserDto> userDtos = users.stream()
                .filter(userDto -> userDto.getUsername().equals(addUserDto.getUsername()))
                .collect(Collectors.toList());
        assertEquals(1, userDtos.size());
        assertEquals(addUserDto.getUsername(), userDtos.get(0).getUsername());
        assertEquals(addUserResponse.getBody().getData().intValue(), userDtos.get(0).getUserId());

        ResponseEntity<ResponseMessage<String>> removeUserResponse = removeUserRequest(userDtos.get(0).getUserId());

        assertEquals(HttpStatus.OK, removeUserResponse.getStatusCode());
    }

    private ResponseEntity<ResponseMessage<Integer>> addUserRequest(AddUserDto addUserDto) {
        return testRestTemplate.exchange(discoverControllerPath() + "/add", HttpMethod.POST, new HttpEntity<>(addUserDto), new ParameterizedTypeReference<ResponseMessage<Integer>>() {
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