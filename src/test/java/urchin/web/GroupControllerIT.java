package urchin.web;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import urchin.api.AddGroupDto;
import urchin.api.AddUserDto;
import urchin.api.AddUserToGroupDto;
import urchin.api.GroupDto;
import urchin.api.support.ResponseMessage;
import urchin.domain.model.GroupId;
import urchin.domain.model.UserId;
import urchin.testutil.TestApplication;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static urchin.testutil.UnixUserAndGroupCleanup.GROUP_PREFIX;
import static urchin.testutil.UnixUserAndGroupCleanup.USERNAME_PREFIX;
import static urchin.web.UserControllerIT.PASSWORD;

public class GroupControllerIT extends TestApplication {

    private UserId userId;

    @Before
    public void setup() {
        userId = new UserId(addUserRequest(new AddUserDto(USERNAME_PREFIX + System.currentTimeMillis(), PASSWORD)).getBody().getData());
    }

    @After
    public void tearDown() {
        removeUserRequest(userId.getId());
    }

    @Test
    public void addGroupAndAddUserToGroupAndRemoveGroup() {
        AddGroupDto addGroupDto = new AddGroupDto(GROUP_PREFIX + System.currentTimeMillis());

        ResponseEntity<ResponseMessage<Integer>> addGroupResponse = addGroupRequest(addGroupDto);

        assertEquals(HttpStatus.OK, addGroupResponse.getStatusCode());
        GroupId groupId = new GroupId(addGroupResponse.getBody().getData());
        assertTrue(groupId.getId() > 0);

        ResponseEntity<ResponseMessage<List<GroupDto>>> groupsResponse = getGroupsRequest();

        assertEquals(HttpStatus.OK, groupsResponse.getStatusCode());
        List<GroupDto> groups = groupsResponse.getBody().getData();
        assertFalse(groups.isEmpty());
        List<GroupDto> groupDtos = groups.stream()
                .filter(groupDto -> groupDto.getName().equals(addGroupDto.getName()))
                .collect(Collectors.toList());
        assertEquals(1, groupDtos.size());

        assertEquals(groupId.getId(), groupDtos.get(0).getGroupId());
        assertEquals(addGroupDto.getName(), groupDtos.get(0).getName());

        AddUserToGroupDto addUserToGroupDto = new AddUserToGroupDto(groupId.getId(), userId.getId());
        ResponseEntity<ResponseMessage<String>> addUserToGroupResponse = addUserToGroupRequest(addUserToGroupDto);

        assertEquals(HttpStatus.OK, addUserToGroupResponse.getStatusCode());

        ResponseEntity<ResponseMessage<String>> removeGroupResponse = removeGroupRequest(groupDtos.get(0).getGroupId());

        assertEquals(HttpStatus.OK, removeGroupResponse.getStatusCode());
    }

    private ResponseEntity<ResponseMessage<Integer>> addGroupRequest(AddGroupDto addGroupDto) {
        return testRestTemplate.exchange(discoverControllerPath() + "/add", HttpMethod.POST, new HttpEntity<>(addGroupDto), new ParameterizedTypeReference<ResponseMessage<Integer>>() {
        });
    }

    private ResponseEntity<ResponseMessage<List<GroupDto>>> getGroupsRequest() {
        return testRestTemplate.exchange(discoverControllerPath(), HttpMethod.GET, null, new ParameterizedTypeReference<ResponseMessage<List<GroupDto>>>() {
        });
    }

    private ResponseEntity<ResponseMessage<String>> removeGroupRequest(int groupId) {
        return testRestTemplate.exchange(discoverControllerPath() + "/" + groupId, HttpMethod.DELETE, null, new ParameterizedTypeReference<ResponseMessage<String>>() {
        });
    }

    private ResponseEntity<ResponseMessage<String>> addUserToGroupRequest(AddUserToGroupDto addUserToGroupDto) {
        return testRestTemplate.exchange(discoverControllerPath() + "/add-user", HttpMethod.POST, new HttpEntity<>(addUserToGroupDto), new ParameterizedTypeReference<ResponseMessage<String>>() {
        });
    }

    private ResponseEntity<ResponseMessage<Integer>> addUserRequest(AddUserDto addUserDto) {
        return testRestTemplate.exchange(discoverControllerPath(UserController.class) + "/add", HttpMethod.POST, new HttpEntity<>(addUserDto), new ParameterizedTypeReference<ResponseMessage<Integer>>() {
        });
    }

    private ResponseEntity<ResponseMessage<String>> removeUserRequest(int userId) {
        return testRestTemplate.exchange(discoverControllerPath(UserController.class) + "/" + userId, HttpMethod.DELETE, null, new ParameterizedTypeReference<ResponseMessage<String>>() {
        });
    }
}