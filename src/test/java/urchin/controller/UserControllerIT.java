package urchin.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import urchin.controller.api.IdDto;
import urchin.controller.api.MessageDto;
import urchin.controller.api.group.*;
import urchin.controller.api.user.AddUserDto;
import urchin.controller.api.user.ImmutableAddUserDto;
import urchin.controller.api.user.UserDto;
import urchin.testutil.TestApplication;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static urchin.testutil.UnixUserAndGroupCleanup.GROUP_PREFIX;
import static urchin.testutil.UnixUserAndGroupCleanup.USERNAME_PREFIX;

public class UserControllerIT extends TestApplication {

    static final String PASSWORD = "superSecret";
    private AddUserDto addUserDto;

    @Before
    public void setUp() {
        addUserDto = ImmutableAddUserDto.builder()
                .username(USERNAME_PREFIX + System.currentTimeMillis())
                .password(PASSWORD)
                .build();
    }

    @Test
    public void addAndRemoveUser() {
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

    @Test
    public void getUser() {
        ResponseEntity<IdDto> addUserResponse = addUserRequest(addUserDto);
        Integer userId = addUserResponse.getBody().getId();

        ResponseEntity<UserDto> getUserResponse = getUserRequest(userId);

        assertEquals(HttpStatus.OK, getUserResponse.getStatusCode());
        assertEquals(userId, getUserResponse.getBody().getUserId());
    }

    @Test
    public void getGroupsForUserReturnsGroups() {
        ResponseEntity<IdDto> addUserResponse = addUserRequest(addUserDto);
        ResponseEntity<IdDto> addGroupResponse = addGroupRequest(ImmutableAddGroupDto.of(GROUP_PREFIX + System.currentTimeMillis()));
        int userId = addUserResponse.getBody().getId();
        int groupId = addGroupResponse.getBody().getId();
        AddUserToGroupDto addUserToGroupDto = ImmutableAddUserToGroupDto.builder()
                .groupId(groupId)
                .userId(userId)
                .build();
        addUserToGroupRequest(addUserToGroupDto);

        ResponseEntity<GroupDto[]> getGroupsForUserResponse = getGroupsForUserRequest(userId);

        assertEquals(HttpStatus.OK, getGroupsForUserResponse.getStatusCode());
        assertNotNull(getGroupsForUserResponse.getBody());
        assertTrue(getGroupsForUserResponse.getBody().length > 0);
    }

    private ResponseEntity<IdDto> addUserRequest(AddUserDto addUserDto) {
        return testRestTemplate.postForEntity(discoverControllerPath() + "/add", addUserDto, IdDto.class);
    }

    private ResponseEntity<UserDto> getUserRequest(int userId) {
        return testRestTemplate.getForEntity(discoverControllerPath() + "/" + userId, UserDto.class);
    }

    private ResponseEntity<UserDto[]> getUsersRequest() {
        return testRestTemplate.getForEntity(discoverControllerPath(), UserDto[].class);
    }

    private ResponseEntity<MessageDto> removeUserRequest(int userId) {
        return testRestTemplate.exchange(discoverControllerPath() + "/" + userId, HttpMethod.DELETE, null, new ParameterizedTypeReference<MessageDto>() {
        });
    }

    private ResponseEntity<GroupDto[]> getGroupsForUserRequest(int userId) {
        return testRestTemplate.getForEntity(discoverControllerPath() + "/" + userId + "/groups", GroupDto[].class);
    }

    private ResponseEntity<IdDto> addGroupRequest(AddGroupDto addGroupDto) {
        return testRestTemplate.postForEntity(discoverControllerPath(GroupController.class) + "/add", addGroupDto, IdDto.class);
    }

    private ResponseEntity<MessageDto> addUserToGroupRequest(AddUserToGroupDto addUserToGroupDto) {
        return testRestTemplate.postForEntity(discoverControllerPath(GroupController.class) + "/user", addUserToGroupDto, MessageDto.class);
    }
}