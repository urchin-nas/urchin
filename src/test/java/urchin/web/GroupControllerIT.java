package urchin.web;

import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import urchin.api.AddGroupApi;
import urchin.api.GroupApi;
import urchin.api.GroupsApi;
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
        AddGroupApi addGroupApi = new AddGroupApi(GROUP_PREFIX + System.currentTimeMillis());

        ResponseEntity<ResponseMessage<String>> addUserResponse = addGroupRequest(addGroupApi);

        assertEquals(HttpStatus.OK, addUserResponse.getStatusCode());

        ResponseEntity<ResponseMessage<GroupsApi>> groupsResponse = getGroupsRequest();

        assertEquals(HttpStatus.OK, groupsResponse.getStatusCode());
        List<GroupApi> groups = groupsResponse.getBody().getData().getGroups();
        assertFalse(groups.isEmpty());
        List<GroupApi> groupApis = groups.stream()
                .filter(groupApi -> groupApi.getName().equals(addGroupApi.getName()))
                .collect(Collectors.toList());
        assertEquals(1, groupApis.size());
        assertEquals(addGroupApi.getName(), groupApis.get(0).getName());

        ResponseEntity<ResponseMessage<String>> removeGroupResponse = removeGroupRequest(groupApis.get(0).getGroupId());

        assertEquals(HttpStatus.OK, removeGroupResponse.getStatusCode());

    }

    private ResponseEntity<ResponseMessage<String>> addGroupRequest(AddGroupApi addGroupApi) {
        return testRestTemplate.exchange(discoverControllerPath() + "/add", HttpMethod.POST, new HttpEntity<>(addGroupApi), new ParameterizedTypeReference<ResponseMessage<String>>() {
        });
    }

    private ResponseEntity<ResponseMessage<GroupsApi>> getGroupsRequest() {
        return testRestTemplate.exchange(discoverControllerPath(), HttpMethod.GET, null, new ParameterizedTypeReference<ResponseMessage<GroupsApi>>() {
        });
    }

    private ResponseEntity<ResponseMessage<String>> removeGroupRequest(int groupId) {
        return testRestTemplate.exchange(discoverControllerPath() + "/" + groupId, HttpMethod.DELETE, null, new ParameterizedTypeReference<ResponseMessage<String>>() {
        });
    }
}