package urchin.controller;

import org.junit.After;
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
import urchin.model.group.GroupId;
import urchin.model.user.UserId;
import urchin.testutil.TestApplication;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static urchin.controller.UserControllerIT.PASSWORD;
import static urchin.testutil.UnixUserAndGroupCleanup.GROUP_PREFIX;
import static urchin.testutil.UnixUserAndGroupCleanup.USERNAME_PREFIX;

public class GroupControllerIT extends TestApplication {

    private UserId userId;
    private AddGroupDto addGroupDto;

    @Before
    public void setup() {
        AddUserDto addUserDto = ImmutableAddUserDto.builder()
                .username(USERNAME_PREFIX + System.currentTimeMillis())
                .password(PASSWORD)
                .build();
        ResponseEntity<IdDto> addUserResponse = addUserRequest(addUserDto);
        userId = UserId.of(addUserResponse.getBody().getId());

        addGroupDto = ImmutableAddGroupDto.of(GROUP_PREFIX + System.currentTimeMillis());
    }

    @After
    public void tearDown() {
        removeUserRequest(userId.getValue());
    }

    @Test
    public void addGroupAndAddUserToGroupAndRemoveUseFromGroupAndRemoveGroup() {

        //Add group
        ResponseEntity<IdDto> addGroupResponse = addGroupRequest(addGroupDto);

        assertEquals(HttpStatus.OK, addGroupResponse.getStatusCode());
        GroupId groupId = GroupId.of(addGroupResponse.getBody().getId());
        assertTrue(groupId.getValue() > 0);

        //Get groups

        ResponseEntity<GroupDto[]> groupsResponse = getGroupsRequest();

        assertEquals(HttpStatus.OK, groupsResponse.getStatusCode());
        List<GroupDto> groups = asList(groupsResponse.getBody());
        assertFalse(groups.isEmpty());
        List<GroupDto> groupDtos = groups.stream()
                .filter(groupDto -> groupDto.getGroupName().equals(addGroupDto.getGroupName()))
                .collect(Collectors.toList());
        assertEquals(1, groupDtos.size());
        assertEquals(groupId.getValue(), groupDtos.get(0).getGroupId());
        assertEquals(addGroupDto.getGroupName(), groupDtos.get(0).getGroupName());

        //Add user to group

        AddUserToGroupDto addUserToGroupDto = ImmutableAddUserToGroupDto.builder()
                .groupId(groupId.getValue())
                .userId(userId.getValue())
                .build();
        ResponseEntity<MessageDto> addUserToGroupResponse = addUserToGroupRequest(addUserToGroupDto);

        assertEquals(HttpStatus.OK, addUserToGroupResponse.getStatusCode());

        //Remove user from group

        ResponseEntity<MessageDto> removeUserFromGroupResponse = removeUserFromGroupRequest(userId, groupId);

        assertEquals(HttpStatus.OK, removeUserFromGroupResponse.getStatusCode());

        //Remove group

        ResponseEntity<MessageDto> removeGroupResponse = removeGroupRequest(groupDtos.get(0).getGroupId());

        assertEquals(HttpStatus.OK, removeGroupResponse.getStatusCode());
    }

    @Test
    public void getGroup() {
        ResponseEntity<IdDto> addGroupResponse = addGroupRequest(addGroupDto);
        Integer groupId = addGroupResponse.getBody().getId();

        ResponseEntity<GroupDto> getGroupResponse = getGroupRequest(groupId);

        assertEquals(HttpStatus.OK, getGroupResponse.getStatusCode());
        assertEquals(groupId, getGroupResponse.getBody().getGroupId());
    }

    @Test
    public void getUsersForGroupReturnsUsers() {
        ResponseEntity<IdDto> addGroupResponse = addGroupRequest(ImmutableAddGroupDto.of(GROUP_PREFIX + System.currentTimeMillis()));
        int groupId = addGroupResponse.getBody().getId();
        AddUserToGroupDto addUserToGroupDto = ImmutableAddUserToGroupDto.builder()
                .groupId(groupId)
                .userId(userId.getValue())
                .build();
        addUserToGroupRequest(addUserToGroupDto);

        ResponseEntity<UserDto[]> usersForGroupResponse = getUsersForGroupRequest(groupId);

        assertEquals(HttpStatus.OK, usersForGroupResponse.getStatusCode());
        assertNotNull(usersForGroupResponse.getBody());
        assertTrue(usersForGroupResponse.getBody().length > 0);
    }

    private ResponseEntity<IdDto> addGroupRequest(AddGroupDto addGroupDto) {
        return testRestTemplate.postForEntity(discoverControllerPath() + "/add", addGroupDto, IdDto.class);
    }

    private ResponseEntity<GroupDto[]> getGroupsRequest() {
        return testRestTemplate.getForEntity(discoverControllerPath(), GroupDto[].class);
    }

    private ResponseEntity<GroupDto> getGroupRequest(int groupId) {
        return testRestTemplate.getForEntity(discoverControllerPath() + "/" + groupId, GroupDto.class);
    }

    private ResponseEntity<MessageDto> removeGroupRequest(int groupId) {
        return testRestTemplate.exchange(discoverControllerPath() + "/" + groupId, HttpMethod.DELETE, null, new ParameterizedTypeReference<MessageDto>() {
        });
    }

    private ResponseEntity<MessageDto> addUserToGroupRequest(AddUserToGroupDto addUserToGroupDto) {
        return testRestTemplate.postForEntity(discoverControllerPath() + "/user", addUserToGroupDto, MessageDto.class);
    }

    private ResponseEntity<MessageDto> removeUserFromGroupRequest(UserId userId, GroupId groupId) {
        return testRestTemplate.exchange(discoverControllerPath() + "/" + groupId.getValue() + "/user/" + userId.getValue(), HttpMethod.DELETE, null, new ParameterizedTypeReference<MessageDto>() {
        });
    }

    private ResponseEntity<IdDto> addUserRequest(AddUserDto addUserDto) {
        return testRestTemplate.postForEntity(discoverControllerPath(UserController.class) + "/add", addUserDto, IdDto.class);
    }

    private ResponseEntity<MessageDto> removeUserRequest(int userId) {
        return testRestTemplate.exchange(discoverControllerPath(UserController.class) + "/" + userId, HttpMethod.DELETE, null, new ParameterizedTypeReference<MessageDto>() {
        });
    }

    private ResponseEntity<UserDto[]> getUsersForGroupRequest(int groupId) {
        return testRestTemplate.getForEntity(discoverControllerPath() + "/" + groupId + "/users", UserDto[].class);
    }
}