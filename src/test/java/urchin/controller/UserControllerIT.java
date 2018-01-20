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

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.assertj.core.api.Assertions.assertThat;
import static urchin.testutil.UnixUserAndGroupCleanup.GROUP_PREFIX;
import static urchin.testutil.UnixUserAndGroupCleanup.USERNAME_PREFIX;

public class UserControllerIT extends TestApplication {

    private AddUserRequest addUserRequest;

    @Before
    public void setUp() {
        addUserRequest = ImmutableAddUserRequest.builder()
                .username(USERNAME_PREFIX + System.currentTimeMillis())
                .password(randomAlphanumeric(10))
                .build();
    }

    @Test
    public void addAndRemoveUser() {
        ResponseEntity<IdResponse> addUserResponse = addUserRequest(addUserRequest);

        assertThat(addUserResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(addUserResponse.getBody().getId() > 0).isTrue();

        ResponseEntity<UserResponse[]> usersResponse = getUsersRequest();

        assertThat(usersResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<UserResponse> users = Arrays.asList(usersResponse.getBody());
        assertThat(users.isEmpty()).isFalse();
        List<UserResponse> userResponses = users.stream()
                .filter(userResponse -> userResponse.getUsername().equals(addUserRequest.getUsername()))
                .collect(Collectors.toList());
        assertThat(userResponses).hasSize(1);
        assertThat(userResponses.get(0).getUsername()).isEqualTo(addUserRequest.getUsername());
        assertThat(userResponses.get(0).getUserId()).isEqualTo(addUserResponse.getBody().getId());

        ResponseEntity<MessageResponse> removeUserResponse = removeUserRequest(userResponses.get(0).getUserId());

        assertThat(removeUserResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getUser() {
        ResponseEntity<IdResponse> addUserResponse = addUserRequest(addUserRequest);
        Integer userId = addUserResponse.getBody().getId();

        ResponseEntity<UserResponse> getUserResponse = getUserRequest(userId);

        assertThat(getUserResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getUserResponse.getBody().getUserId()).isEqualTo(userId);
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

        assertThat(getGroupsForUserResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getGroupsForUserResponse.getBody()).isNotNull();
        assertThat(getGroupsForUserResponse.getBody().length > 0).isTrue();
    }

    @Test
    public void addUserWithEmptyRequestReturnsErrorResponse() {
        AddUserRequest emptyAddUserRequest = ImmutableAddUserRequest.builder()
                .build();

        ResponseEntity<ErrorResponse> response = testRestTemplate.postForEntity(discoverControllerPath() + "/add", emptyAddUserRequest, ErrorResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        ErrorResponse errorResponse = response.getBody();
        assertThat(errorResponse.getErrorCode()).isEqualTo(ErrorCode.VALIDATION_ERROR);
        assertThat(errorResponse.getMessage()).isEqualTo(ControllerAdvice.VALIDATION_ERROR_MESSAGE);
        Map<String, List<String>> fieldErrors = errorResponse.getFieldErrors();
        assertThat(fieldErrors).hasSize(2);
        assertThat(fieldErrors.containsKey("password")).isTrue();
        assertThat(fieldErrors.containsKey("username")).isTrue();
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