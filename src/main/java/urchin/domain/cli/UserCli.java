package urchin.domain.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import urchin.domain.cli.user.*;
import urchin.domain.model.User;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserCli {

    private final AddUserCommand addUserCommand;
    private final CheckIfUsernameExistCommand checkIfUsernameExistCommand;
    private final RemoveUserCommand removeUserCommand;
    private final SetUserPasswordCommand setUserPasswordCommand;
    private final ListUsersCommand listUsersCommand;

    @Autowired
    public UserCli(
            AddUserCommand addUserCommand,
            CheckIfUsernameExistCommand checkIfUsernameExistCommand,
            RemoveUserCommand removeUserCommand,
            SetUserPasswordCommand setUserPasswordCommand,
            ListUsersCommand listUsersCommand
    ) {
        this.addUserCommand = addUserCommand;
        this.checkIfUsernameExistCommand = checkIfUsernameExistCommand;
        this.removeUserCommand = removeUserCommand;
        this.setUserPasswordCommand = setUserPasswordCommand;
        this.listUsersCommand = listUsersCommand;
    }

    public void addUser(User user) {
        addUserCommand.execute(user);
    }

    public boolean checkIfUsernameExist(String username) {
        return checkIfUsernameExistCommand.execute(username);
    }

    public void removeUser(User user) {
        removeUserCommand.execute(user);
    }

    public void setSetUserPassword(User user, String password) {
        setUserPasswordCommand.execute(user, password);
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

}
