package urchin.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import urchin.cli.user.*;
import urchin.model.group.GroupName;
import urchin.model.user.Password;
import urchin.model.user.User;
import urchin.model.user.Username;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public void addUser(Username username) {
        addUserCommand.execute(username);
    }

    public boolean checkIfUsernameExist(Username username) {
        return checkIfUsernameExistCommand.execute(username);
    }

    public void removeUser(Username username) {
        removeUserCommand.execute(username);
    }

    public void setSetUserPassword(Username username, Password password) {
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

    public List<GroupName> listGroupsForUser(User user) {
        return Arrays.stream(listGroupsForUserCommand.execute(user).get().split(":")[1].trim().split(" "))
                .map(GroupName::of)
                .collect(Collectors.toList());
    }

}
