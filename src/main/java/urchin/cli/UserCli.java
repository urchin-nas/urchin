package urchin.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import urchin.cli.user.*;
import urchin.model.group.GroupName;
import urchin.model.user.LinuxUser;
import urchin.model.user.Password;
import urchin.model.user.User;
import urchin.model.user.Username;

import java.util.List;

@Repository
public class UserCli {

    private final AddUserCommand addUserCommand;
    private final CheckIfUsernameExistCommand checkIfUsernameExistCommand;
    private final RemoveUserCommand removeUserCommand;
    private final SetUserPasswordCommand setUserPasswordCommand;
    private final ListUsersCommand listUsersCommand;
    private final ListGroupsForUserCommand listGroupsForUserCommand;
    private final WhoAmICommand whoAmICommand;

    @Autowired
    public UserCli(
            AddUserCommand addUserCommand,
            CheckIfUsernameExistCommand checkIfUsernameExistCommand,
            RemoveUserCommand removeUserCommand,
            SetUserPasswordCommand setUserPasswordCommand,
            ListUsersCommand listUsersCommand,
            ListGroupsForUserCommand listGroupsForUserCommand,
            WhoAmICommand whoAmICommand) {
        this.addUserCommand = addUserCommand;
        this.checkIfUsernameExistCommand = checkIfUsernameExistCommand;
        this.removeUserCommand = removeUserCommand;
        this.setUserPasswordCommand = setUserPasswordCommand;
        this.listUsersCommand = listUsersCommand;
        this.listGroupsForUserCommand = listGroupsForUserCommand;
        this.whoAmICommand = whoAmICommand;
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

    public void setUserPassword(Username username, Password password) {
        setUserPasswordCommand.execute(username, password);
    }

    public List<String> listUsers() {
        return listUsersCommand.execute();
    }

    public List<GroupName> listGroupsForUser(User user) {
        return listGroupsForUserCommand.execute(user);
    }

    public LinuxUser whoAmI() {
        return whoAmICommand.execute();
    }

}
