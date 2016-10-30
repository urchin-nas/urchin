package urchin.api;

public class UserApi {

    private int userId;
    private String username;

    private UserApi() {
    }

    public UserApi(int userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

}
