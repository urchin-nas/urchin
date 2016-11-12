package urchin.domain.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import urchin.domain.cli.user.AddUserCommand;
import urchin.domain.cli.user.CheckIfUsernameExistCommand;
import urchin.domain.cli.user.RemoveUserCommand;
import urchin.domain.cli.user.SetUserPasswordCommand;
import urchin.domain.model.User;

@Repository
public class UserCli {

    private final AddUserCommand addUserCommand;
    private final CheckIfUsernameExistCommand checkIfUsernameExistCommand;
    private final RemoveUserCommand removeUserCommand;
    private final SetUserPasswordCommand setUserPasswordCommand;

    @Autowired
    public UserCli(
            AddUserCommand addUserCommand,
            CheckIfUsernameExistCommand checkIfUsernameExistCommand,
            RemoveUserCommand removeUserCommand,
            SetUserPasswordCommand setUserPasswordCommand
    ) {
        this.addUserCommand = addUserCommand;
        this.checkIfUsernameExistCommand = checkIfUsernameExistCommand;
        this.removeUserCommand = removeUserCommand;
        this.setUserPasswordCommand = setUserPasswordCommand;
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

}
