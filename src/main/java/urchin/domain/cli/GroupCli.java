package urchin.domain.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import urchin.domain.cli.common.CommandException;
import urchin.domain.cli.group.*;
import urchin.domain.model.Group;
import urchin.domain.model.User;

import static java.util.Arrays.stream;

@Repository
public class GroupCli {

    private final AddGroupCommand addGroupCommand;
    private final GetGroupEntriesCommand getGroupEntriesCommand;
    private final RemoveGroupCommand removeGroupCommand;
    private final AddUserToGroupCommand addUserToGroupCommand;
    private final RemoveUserFromGroupCommand removeUserFromGroupCommand;

    @Autowired
    public GroupCli(
            AddGroupCommand addGroupCommand,
            GetGroupEntriesCommand getGroupEntriesCommand,
            RemoveGroupCommand removeGroupCommand,
            AddUserToGroupCommand addUserToGroupCommand,
            RemoveUserFromGroupCommand removeUserFromGroupCommand
    ) {
        this.addGroupCommand = addGroupCommand;
        this.getGroupEntriesCommand = getGroupEntriesCommand;
        this.removeGroupCommand = removeGroupCommand;
        this.addUserToGroupCommand = addUserToGroupCommand;
        this.removeUserFromGroupCommand = removeUserFromGroupCommand;
    }

    public void addGroup(Group group) {
        addGroupCommand.execute(group);
    }

    public boolean checkIfGroupExist(String groupName) {
        try {
            return getGroupEntriesCommand.execute(groupName).isPresent();
        } catch (CommandException e) {
            if (e.getExitValue() == 2) {
                return false;
            } else {
                throw e;
            }
        }
    }

    public void removeGroup(Group group) {
        removeGroupCommand.execute(group);
    }

    public void addUserToGroup(User user, Group group) {
        addUserToGroupCommand.execute(user, group);
    }

    public void removeUserFromGroup(User user, Group group) {
        removeUserFromGroupCommand.execute(user, group);
    }

    public boolean checkIfUserIsInGroup(User user, Group group) {
        try {
            String groupEntries = getGroupEntriesCommand.execute(group.getName()).get();
            String[] users = groupEntries.split(":")[3].split(",");
            return stream(users)
                    .filter(usr -> usr.replace("\n", "").equals(user.getUsername()))
                    .count() == 1;
        } catch (CommandException e) {
            return false;
        }
    }
}
