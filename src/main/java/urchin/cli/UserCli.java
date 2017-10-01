package urchin.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import urchin.cli.user.*;
import urchin.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class UserCli {

    private final AddUserCommand addUserCommand;
    private final CheckIfUsernameExistCommand checkIfUsernameExistCommand;
    private final RemoveUserCommand removeUserCommand;
    private final SetUserPasswordCommand setUserPasswordCommand;
    private final ListUsersCommand listUsersCommand;
    private final ListGroupsForUserCommand listGroupsForUserCommand;

    @Autowired
    public UserCli(
            AddUserCommand addUserCommand,
            CheckIfUsernameExistCommand checkIfUsernameExistCommand,
            RemoveUserCommand removeUserCommand,
            SetUserPasswordCommand setUserPasswordCommand,
            ListUsersCommand listUsersCommand,
            ListGroupsForUserCommand listGroupsForUserCommand
    ) {
        this.addUserCommand = addUserCommand;
        this.checkIfUsernameExistCommand = checkIfUsernameExistCommand;
        this.removeUserCommand = removeUserCommand;
        this.setUserPasswordCommand = setUserPasswordCommand;
        this.listUsersCommand = listUsersCommand;
        this.listGroupsForUserCommand = listGroupsForUserCommand;
    }

    public void addUser(String username) {
        addUserCommand.execute(username);
    }

    public boolean checkIfUsernameExist(String username) {
        return checkIfUsernameExistCommand.execute(username);
    }

    public void removeUser(String username) {
        removeUserCommand.execute(username);
    }

    public void setSetUserPassword(String username, String password) {
        setUserPasswordCommand.execute(username, password);
    }

    public List<String> listUsers() {
        String response = listUsersCommand.execute().get();
        String[] users = response.split("\n");
        List<String> unixUsers = new ArrayList<>();
        for (String user : users) {
            String[] userValues = user.split(":");
            unixUsers.add(userValues[0]);
        }
        return unixUsers;
    }

    public List<String> listGroupsForUser(User user) {
        return Arrays.asList(listGroupsForUserCommand.execute(user).get().split(":")[1].trim().split(" "));
    }

}
