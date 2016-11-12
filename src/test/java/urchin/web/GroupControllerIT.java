package urchin.web;

import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import urchin.api.AddGroupDto;
import urchin.api.GroupDto;
import urchin.api.GroupsDto;
import urchin.api.support.ResponseMessage;
import urchin.testutil.TestApplication;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class GroupControllerIT extends TestApplication {

    private static final String GROUP_PREFIX = "urchin_";

    @Test
    public void addAndRemoveGroup() {
        AddGroupDto addGroupDto = new AddGroupDto(GROUP_PREFIX + System.currentTimeMillis());

        ResponseEntity<ResponseMessage<String>> addUserResponse = addGroupRequest(addGroupDto);

        assertEquals(HttpStatus.OK, addUserResponse.getStatusCode());

        ResponseEntity<ResponseMessage<GroupsDto>> groupsResponse = getGroupsRequest();

        assertEquals(HttpStatus.OK, groupsResponse.getStatusCode());
        List<GroupDto> groups = groupsResponse.getBody().getData().getGroups();
        assertFalse(groups.isEmpty());
        List<GroupDto> groupDtos = groups.stream()
                .filter(groupDto -> groupDto.getName().equals(addGroupDto.getName()))
                .collect(Collectors.toList());
        assertEquals(1, groupDtos.size());
        assertEquals(addGroupDto.getName(), groupDtos.get(0).getName());

        ResponseEntity<ResponseMessage<String>> removeGroupResponse = removeGroupRequest(groupDtos.get(0).getGroupId());

        assertEquals(HttpStatus.OK, removeGroupResponse.getStatusCode());

    }

    private ResponseEntity<ResponseMessage<String>> addGroupRequest(AddGroupDto addGroupDto) {
        return testRestTemplate.exchange(discoverControllerPath() + "/add", HttpMethod.POST, new HttpEntity<>(addGroupDto), new ParameterizedTypeReference<ResponseMessage<String>>() {
        });
    }

    private ResponseEntity<ResponseMessage<GroupsDto>> getGroupsRequest() {
        return testRestTemplate.exchange(discoverControllerPath(), HttpMethod.GET, null, new ParameterizedTypeReference<ResponseMessage<GroupsDto>>() {
        });
    }

    private ResponseEntity<ResponseMessage<String>> removeGroupRequest(int groupId) {
        return testRestTemplate.exchange(discoverControllerPath() + "/" + groupId, HttpMethod.DELETE, null, new ParameterizedTypeReference<ResponseMessage<String>>() {
        });
    }
}