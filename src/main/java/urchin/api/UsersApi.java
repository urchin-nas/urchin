package urchin.api;

import java.util.List;

public class UsersApi {

    private List<UserApi> users;

    private UsersApi() {
    }

    public UsersApi(List<UserApi> userApis) {
        this.users = userApis;
    }

    public List<UserApi> getUsers() {
        return users;
    }

}
