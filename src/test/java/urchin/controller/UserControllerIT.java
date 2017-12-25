package urchin.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import urchin.controller.api.ErrorCode;
import urchin.controller.api.ErrorResponse;
import urchin.controller.api.IdResponse;
import urchin.controller.api.MessageResponse;
import urchin.controller.api.group.*;
import urchin.controller.api.user.AddUserRequest;
import urchin.controller.api.user.ImmutableAddUserRequest;
import urchin.controller.api.user.UserResponse;
import urchin.testutil.TestApplication;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static urchin.testutil.UnixUserAndGroupCleanup.GROUP_PREFIX;
import static urchin.testutil.UnixUserAndGroupCleanup.USERNAME_PREFIX;

public class UserControllerIT extends TestApplication {

    static final String PASSWORD = "superSecret";
    private AddUserRequest addUserRequest;

    @Before
    public void setUp() {
        addUserRequest = ImmutableAddUserRequest.builder()
                .username(USERNAME_PREFIX + System.currentTimeMillis())
                .password(PASSWORD)
                .build();
    }

    @Test
    public void addAndRemoveUser() {
        ResponseEntity<IdResponse> addUserResponse = addUserRequest(addUserRequest);

        assertEquals(HttpStatus.OK, addUserResponse.getStatusCode());
        assertTrue(addUserResponse.getBody().getId() > 0);

        ResponseEntity<UserResponse[]> usersResponse = getUsersRequest();

        assertEquals(HttpStatus.OK, usersResponse.getStatusCode());
        List<UserResponse> users = Arrays.asList(usersResponse.getBody());
        assertFalse(users.isEmpty());
        List<UserResponse> userResponses = users.stream()
                .filter(userResponse -> userResponse.getUsername().equals(addUserRequest.getUsername()))
                .collect(Collectors.toList());
        assertEquals(1, userResponses.size());
        assertEquals(addUserRequest.getUsername(), userResponses.get(0).getUsername());
        assertEquals(addUserResponse.getBody().getId(), userResponses.get(0).getUserId());

        ResponseEntity<MessageResponse> removeUserResponse = removeUserRequest(userResponses.get(0).getUserId());

        assertEquals(HttpStatus.OK, removeUserResponse.getStatusCode());
    }

    @Test
    public void getUser() {
        ResponseEntity<IdResponse> addUserResponse = addUserRequest(addUserRequest);
        Integer userId = addUserResponse.getBody().getId();

        ResponseEntity<UserResponse> getUserResponse = getUserRequest(userId);

        assertEquals(HttpStatus.OK, getUserResponse.getStatusCode());
        assertEquals(userId, getUserResponse.getBody().getUserId());
    }

    @Test
    public void getGroupsForUserReturnsGroups() {
        ResponseEntity<IdResponse> addUserResponse = addUserRequest(addUserRequest);
        ResponseEntity<IdResponse> addGroupResponse = addGroupRequest(ImmutableAddGroupRequest.of(GROUP_PREFIX + System.currentTimeMillis()));
        int userId = addUserResponse.getBody().getId();
        int groupId = addGroupResponse.getBody().getId();
        AddUserToGroupRequest addUserToGroupRequest = ImmutableAddUserToGroupRequest.builder()
                .groupId(groupId)
                .userId(userId)
                .build();
        addUserToGroupRequest(addUserToGroupRequest);

        ResponseEntity<GroupResponse[]> getGroupsForUserResponse = getGroupsForUserRequest(userId);

        assertEquals(HttpStatus.OK, getGroupsForUserResponse.getStatusCode());
        assertNotNull(getGroupsForUserResponse.getBody());
        assertTrue(getGroupsForUserResponse.getBody().length > 0);
    }

    @Test
    public void addUserWithEmptyRequestReturnsErrorResponse() {
        AddUserRequest emptyAddUserRequest = ImmutableAddUserRequest.builder()
                .build();

        ResponseEntity<ErrorResponse> response = testRestTemplate.postForEntity(discoverControllerPath() + "/add", emptyAddUserRequest, ErrorResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ErrorResponse errorResponse = response.getBody();
        assertEquals(ErrorCode.VALIDATION_ERROR, errorResponse.getErrorCode());
        assertEquals(ControllerAdvice.VALIDATION_ERROR_MESSAGE, errorResponse.getMessage());
        Map<String, List<String>> fieldErrors = errorResponse.getFieldErrors();
        assertEquals(2, fieldErrors.size());
        assertTrue(fieldErrors.containsKey("password"));
        assertTrue(fieldErrors.containsKey("username"));
    }

    private ResponseEntity<IdResponse> addUserRequest(AddUserRequest addUserRequest) {
        return testRestTemplate.postForEntity(discoverControllerPath() + "/add", addUserRequest, IdResponse.class);
    }

    private ResponseEntity<UserResponse> getUserRequest(int userId) {
        return testRestTemplate.getForEntity(discoverControllerPath() + "/" + userId, UserResponse.class);
    }

    private ResponseEntity<UserResponse[]> getUsersRequest() {
        return testRestTemplate.getForEntity(discoverControllerPath(), UserResponse[].class);
    }

    private ResponseEntity<MessageResponse> removeUserRequest(int userId) {
        return testRestTemplate.exchange(discoverControllerPath() + "/" + userId, HttpMethod.DELETE, null, new ParameterizedTypeReference<MessageResponse>() {
        });
    }

    private ResponseEntity<GroupResponse[]> getGroupsForUserRequest(int userId) {
        return testRestTemplate.getForEntity(discoverControllerPath() + "/" + userId + "/groups", GroupResponse[].class);
    }

    private ResponseEntity<IdResponse> addGroupRequest(AddGroupRequest addGroupRequest) {
        return testRestTemplate.postForEntity(discoverControllerPath(GroupController.class) + "/add", addGroupRequest, IdResponse.class);
    }

    private ResponseEntity<MessageResponse> addUserToGroupRequest(AddUserToGroupRequest addUserToGroupRequest) {
        return testRestTemplate.postForEntity(discoverControllerPath(GroupController.class) + "/user", addUserToGroupRequest, MessageResponse.class);
    }
}