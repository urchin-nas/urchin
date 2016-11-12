package urchin.api;

public class UserDto {

    private int userId;
    private String username;

    private UserDto() {
    }

    public UserDto(int userId, String username) {
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
