package urchin.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import urchin.cli.user.*;
import urchin.model.group.GroupName;
import urchin.model.user.*;

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
    private final GetShadowCommand getShadowCommand;
    private final VerifyShadowPasswordCommand verifyShadowPasswordCommand;

    @Autowired
    public UserCli(
            AddUserCommand addUserCommand,
            CheckIfUsernameExistCommand checkIfUsernameExistCommand,
            RemoveUserCommand removeUserCommand,
            SetUserPasswordCommand setUserPasswordCommand,
            ListUsersCommand listUsersCommand,
            ListGroupsForUserCommand listGroupsForUserCommand,
            WhoAmICommand whoAmICommand,
            GetShadowCommand getShadowCommand,
            VerifyShadowPasswordCommand verifyShadowPasswordCommand) {
        this.addUserCommand = addUserCommand;
        this.checkIfUsernameExistCommand = checkIfUsernameExistCommand;
        this.removeUserCommand = removeUserCommand;
        this.setUserPasswordCommand = setUserPasswordCommand;
        this.listUsersCommand = listUsersCommand;
        this.listGroupsForUserCommand = listGroupsForUserCommand;
        this.whoAmICommand = whoAmICommand;
        this.getShadowCommand = getShadowCommand;
        this.verifyShadowPasswordCommand = verifyShadowPasswordCommand;
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

    public boolean verifyPassword(LinuxUser linuxUser, Password password) {
        Shadow systemShadow = getShadow(linuxUser);
        return verifyShadowPassword(password, systemShadow);
    }

    Shadow getShadow(LinuxUser linuxUser) {
        return toShadow(getShadowCommand.execute(linuxUser));
    }

    boolean verifyShadowPassword(Password password, Shadow shadow) {
        Shadow newShadow = toShadow(verifyShadowPasswordCommand.execute(password, shadow));
        return newShadow.getEncryptedPassword().equals(shadow.getEncryptedPassword());
    }

    private Shadow toShadow(String response) {
        String[] split = response.trim().split("\\$");
        String id = split[1];
        String salt = split[2];
        String encryptedPassword = split[3].split(":")[0];

        return ImmutableShadow.builder()
                .id(id)
                .salt(salt)
                .encryptedPassword(encryptedPassword)
                .build();
    }

}
