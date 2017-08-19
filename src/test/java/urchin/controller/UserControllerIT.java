package urchin.controller;

import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import urchin.controller.api.AddUserDto;
import urchin.controller.api.IdDto;
import urchin.controller.api.MessageDto;
import urchin.controller.api.UserDto;
import urchin.testutil.TestApplication;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static urchin.testutil.UnixUserAndGroupCleanup.USERNAME_PREFIX;

public class UserControllerIT extends TestApplication {

    static final String PASSWORD = "superSecret";

    @Test
    public void addAndRemoveUser() {
        AddUserDto addUserDto = new AddUserDto(USERNAME_PREFIX + System.currentTimeMillis(), PASSWORD);

        ResponseEntity<IdDto> addUserResponse = addUserRequest(addUserDto);

        assertEquals(HttpStatus.OK, addUserResponse.getStatusCode());
        assertTrue(addUserResponse.getBody().getId() > 0);

        ResponseEntity<UserDto[]> usersResponse = getUsersRequest();

        assertEquals(HttpStatus.OK, usersResponse.getStatusCode());
        List<UserDto> users = Arrays.asList(usersResponse.getBody());
        assertFalse(users.isEmpty());
        List<UserDto> userDtos = users.stream()
                .filter(userDto -> userDto.getUsername().equals(addUserDto.getUsername()))
                .collect(Collectors.toList());
        assertEquals(1, userDtos.size());
        assertEquals(addUserDto.getUsername(), userDtos.get(0).getUsername());
        assertEquals(addUserResponse.getBody().getId(), userDtos.get(0).getUserId());

        ResponseEntity<MessageDto> removeUserResponse = removeUserRequest(userDtos.get(0).getUserId());

        assertEquals(HttpStatus.OK, removeUserResponse.getStatusCode());
    }

    private ResponseEntity<IdDto> addUserRequest(AddUserDto addUserDto) {
        return testRestTemplate.postForEntity(discoverControllerPath() + "/add", addUserDto, IdDto.class);
    }

    private ResponseEntity<UserDto[]> getUsersRequest() {
        return testRestTemplate.getForEntity(discoverControllerPath(), UserDto[].class);
    }

    private ResponseEntity<MessageDto> removeUserRequest(int userId) {
        return testRestTemplate.exchange(discoverControllerPath() + "/" + userId, HttpMethod.DELETE, null, new ParameterizedTypeReference<MessageDto>() {
        });
    }
}