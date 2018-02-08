package urchin.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import urchin.cli.group.*;
import urchin.exception.CommandException;
import urchin.model.group.Group;
import urchin.model.group.GroupName;
import urchin.model.user.User;
import urchin.model.user.Username;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Repository
public class GroupCli {

    private final AddGroupCommand addGroupCommand;
    private final GetGroupEntriesCommand getGroupEntriesCommand;
    private final RemoveGroupCommand removeGroupCommand;
    private final AddUserToGroupCommand addUserToGroupCommand;
    private final RemoveUserFromGroupCommand removeUserFromGroupCommand;
    private final ListGroupsCommand listGroupsCommand;

    @Autowired
    public GroupCli(
            AddGroupCommand addGroupCommand,
            GetGroupEntriesCommand getGroupEntriesCommand,
            RemoveGroupCommand removeGroupCommand,
            AddUserToGroupCommand addUserToGroupCommand,
            RemoveUserFromGroupCommand removeUserFromGroupCommand,
            ListGroupsCommand listGroupsCommand
    ) {
        this.addGroupCommand = addGroupCommand;
        this.getGroupEntriesCommand = getGroupEntriesCommand;
        this.removeGroupCommand = removeGroupCommand;
        this.addUserToGroupCommand = addUserToGroupCommand;
        this.removeUserFromGroupCommand = removeUserFromGroupCommand;
        this.listGroupsCommand = listGroupsCommand;
    }

    public void addGroup(GroupName groupName) {
        addGroupCommand.execute(groupName);
    }

    public boolean checkIfGroupExist(GroupName groupName) {
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

    public void removeGroup(GroupName groupName) {
        removeGroupCommand.execute(groupName);
    }

    public void addUserToGroup(User user, Group group) {
        addUserToGroupCommand.execute(user, group);
    }

    public void removeUserFromGroup(User user, Group group) {
        removeUserFromGroupCommand.execute(user, group);
    }

    public boolean checkIfUserIsInGroup(User user, Group group) {
        try {
            String[] users = getUsers(group);
            return stream(users)
                    .filter(usr -> usr.replace("\n", "").equals(user.getUsername().getValue()))
                    .count() == 1;
        } catch (CommandException e) {
            return false;
        }
    }

    public List<GroupName> listGroups() {
        return listGroupsCommand.execute();
    }

    public List<Username> listUsersForGroup(Group group) {
        String[] users = getUsers(group);
        return stream(users)
                .map(usr -> Username.of(usr.replace("\n", "")))
                .collect(Collectors.toList());
    }

    private String[] getUsers(Group group) {
        return getGroupEntriesCommand.execute(group.getName())
                .map(s -> s.split(":")[3].split(","))
                .orElse(new String[0]);
    }
}
