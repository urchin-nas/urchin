package urchin.web;

import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import urchin.api.AddUserApi;
import urchin.api.UserApi;
import urchin.api.UsersApi;
import urchin.api.support.ResponseMessage;
import urchin.testutil.RestApplication;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class UserControllerIT extends RestApplication {

    private static final String USERNAME_PREFIX = "urchin_";
    private static final String PASSWORD = "superSecret";

    @Override
    protected String getPath() {
        return "/users";
    }

    @Test
    public void addAndRemoveUser() {
        AddUserApi addUserApi = new AddUserApi(USERNAME_PREFIX + System.currentTimeMillis(), PASSWORD);

        ResponseEntity<ResponseMessage<String>> addUserResponse = addUserRequest(addUserApi);

        assertEquals(HttpStatus.OK, addUserResponse.getStatusCode());

        ResponseEntity<ResponseMessage<UsersApi>> usersResponse = getUsersRequest();

        assertEquals(HttpStatus.OK, usersResponse.getStatusCode());
        List<UserApi> users = usersResponse.getBody().getData().getUsers();
        assertFalse(users.isEmpty());
        List<UserApi> userApis = users.stream()
                .filter(userApi -> userApi.getUsername().equals(addUserApi.getUsername()))
                .collect(Collectors.toList());
        assertEquals(1, userApis.size());
        assertEquals(addUserApi.getUsername(), userApis.get(0).getUsername());

        ResponseEntity<ResponseMessage<String>> removeUserResponse = removeUserRequest(userApis.get(0).getUserId());

        assertEquals(HttpStatus.OK, addUserResponse.getStatusCode());
    }

    private ResponseEntity<ResponseMessage<String>> addUserRequest(AddUserApi addUserApi) {
        return template.exchange(url + "/add", HttpMethod.POST, new HttpEntity<>(addUserApi), new ParameterizedTypeReference<ResponseMessage<String>>() {
        });
    }

    private ResponseEntity<ResponseMessage<UsersApi>> getUsersRequest() {
        return template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<ResponseMessage<UsersApi>>() {
        });
    }

    private ResponseEntity<ResponseMessage<String>> removeUserRequest(int userId) {
        return template.exchange(url + "/" + userId, HttpMethod.DELETE, null, new ParameterizedTypeReference<ResponseMessage<String>>() {
        });
    }
}