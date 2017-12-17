package urchin.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import urchin.controller.api.IdResponse;
import urchin.controller.api.MessageResponse;
import urchin.controller.api.group.*;
import urchin.controller.api.user.AddUserRequest;
import urchin.controller.api.user.ImmutableAddUserRequest;
import urchin.controller.api.user.UserResponse;
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
    private AddGroupRequest addGroupRequest;

    @Before
    public void setup() {
        AddUserRequest addUserRequest = ImmutableAddUserRequest.builder()
                .username(USERNAME_PREFIX + System.currentTimeMillis())
                .password(PASSWORD)
                .build();
        ResponseEntity<IdResponse> addUserResponse = addUserRequest(addUserRequest);
        userId = UserId.of(addUserResponse.getBody().getId());

        addGroupRequest = ImmutableAddGroupRequest.of(GROUP_PREFIX + System.currentTimeMillis());
    }

    @After
    public void tearDown() {
        removeUserRequest(userId.getValue());
    }

    @Test
    public void addGroupAndAddUserToGroupAndRemoveUseFromGroupAndRemoveGroup() {

        //Add group
        ResponseEntity<IdResponse> addGroupResponse = addGroupRequest(addGroupRequest);

        assertEquals(HttpStatus.OK, addGroupResponse.getStatusCode());
        GroupId groupId = GroupId.of(addGroupResponse.getBody().getId());
        assertTrue(groupId.getValue() > 0);

        //Get groups

        ResponseEntity<GroupResponse[]> groupsResponse = getGroupsRequest();

        assertEquals(HttpStatus.OK, groupsResponse.getStatusCode());
        List<GroupResponse> groups = asList(groupsResponse.getBody());
        assertFalse(groups.isEmpty());
        List<GroupResponse> groupResponses = groups.stream()
                .filter(groupResponse -> groupResponse.getGroupName().equals(addGroupRequest.getGroupName()))
                .collect(Collectors.toList());
        assertEquals(1, groupResponses.size());
        assertEquals(groupId.getValue(), groupResponses.get(0).getGroupId());
        assertEquals(addGroupRequest.getGroupName(), groupResponses.get(0).getGroupName());

        //Add user to group

        AddUserToGroupRequest addUserToGroupRequest = ImmutableAddUserToGroupRequest.builder()
                .groupId(groupId.getValue())
                .userId(userId.getValue())
                .build();
        ResponseEntity<MessageResponse> addUserToGroupResponse = addUserToGroupRequest(addUserToGroupRequest);

        assertEquals(HttpStatus.OK, addUserToGroupResponse.getStatusCode());

        //Remove user from group

        ResponseEntity<MessageResponse> removeUserFromGroupResponse = removeUserFromGroupRequest(userId, groupId);

        assertEquals(HttpStatus.OK, removeUserFromGroupResponse.getStatusCode());

        //Remove group

        ResponseEntity<MessageResponse> removeGroupResponse = removeGroupRequest(groupResponses.get(0).getGroupId());

        assertEquals(HttpStatus.OK, removeGroupResponse.getStatusCode());
    }

    @Test
    public void getGroup() {
        ResponseEntity<IdResponse> addGroupResponse = addGroupRequest(addGroupRequest);
        Integer groupId = addGroupResponse.getBody().getId();

        ResponseEntity<GroupResponse> getGroupResponse = getGroupRequest(groupId);

        assertEquals(HttpStatus.OK, getGroupResponse.getStatusCode());
        assertEquals(groupId, getGroupResponse.getBody().getGroupId());
    }

    @Test
    public void getUsersForGroupReturnsUsers() {
        ResponseEntity<IdResponse> addGroupResponse = addGroupRequest(ImmutableAddGroupRequest.of(GROUP_PREFIX + System.currentTimeMillis()));
        int groupId = addGroupResponse.getBody().getId();
        AddUserToGroupRequest addUserToGroupRequest = ImmutableAddUserToGroupRequest.builder()
                .groupId(groupId)
                .userId(userId.getValue())
                .build();
        addUserToGroupRequest(addUserToGroupRequest);

        ResponseEntity<UserResponse[]> usersForGroupResponse = getUsersForGroupRequest(groupId);

        assertEquals(HttpStatus.OK, usersForGroupResponse.getStatusCode());
        assertNotNull(usersForGroupResponse.getBody());
        assertTrue(usersForGroupResponse.getBody().length > 0);
    }

    private ResponseEntity<IdResponse> addGroupRequest(AddGroupRequest addGroupRequest) {
        return testRestTemplate.postForEntity(discoverControllerPath() + "/add", addGroupRequest, IdResponse.class);
    }

    private ResponseEntity<GroupResponse[]> getGroupsRequest() {
        return testRestTemplate.getForEntity(discoverControllerPath(), GroupResponse[].class);
    }

    private ResponseEntity<GroupResponse> getGroupRequest(int groupId) {
        return testRestTemplate.getForEntity(discoverControllerPath() + "/" + groupId, GroupResponse.class);
    }

    private ResponseEntity<MessageResponse> removeGroupRequest(int groupId) {
        return testRestTemplate.exchange(discoverControllerPath() + "/" + groupId, HttpMethod.DELETE, null, new ParameterizedTypeReference<MessageResponse>() {
        });
    }

    private ResponseEntity<MessageResponse> addUserToGroupRequest(AddUserToGroupRequest addUserToGroupRequest) {
        return testRestTemplate.postForEntity(discoverControllerPath() + "/user", addUserToGroupRequest, MessageResponse.class);
    }

    private ResponseEntity<MessageResponse> removeUserFromGroupRequest(UserId userId, GroupId groupId) {
        return testRestTemplate.exchange(discoverControllerPath() + "/" + groupId.getValue() + "/user/" + userId.getValue(), HttpMethod.DELETE, null, new ParameterizedTypeReference<MessageResponse>() {
        });
    }

    private ResponseEntity<IdResponse> addUserRequest(AddUserRequest addUserRequest) {
        return testRestTemplate.postForEntity(discoverControllerPath(UserController.class) + "/add", addUserRequest, IdResponse.class);
    }

    private ResponseEntity<MessageResponse> removeUserRequest(int userId) {
        return testRestTemplate.exchange(discoverControllerPath(UserController.class) + "/" + userId, HttpMethod.DELETE, null, new ParameterizedTypeReference<MessageResponse>() {
        });
    }

    private ResponseEntity<UserResponse[]> getUsersForGroupRequest(int groupId) {
        return testRestTemplate.getForEntity(discoverControllerPath() + "/" + groupId + "/users", UserResponse[].class);
    }
}