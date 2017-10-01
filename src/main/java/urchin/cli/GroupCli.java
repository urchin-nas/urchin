package urchin.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import urchin.cli.common.CommandException;
import urchin.cli.group.*;
import urchin.model.group.Group;
import urchin.model.group.GroupName;
import urchin.model.user.User;

import java.util.ArrayList;
import java.util.List;

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
            String groupEntries = getGroupEntriesCommand.execute(group.getName()).get();
            String[] users = groupEntries.split(":")[3].split(",");
            return stream(users)
                    .filter(usr -> usr.replace("\n", "").equals(user.getUsername().getValue()))
                    .count() == 1;
        } catch (CommandException e) {
            return false;
        }
    }

    public List<GroupName> listGroups() {
        String response = listGroupsCommand.execute().get();
        String[] groups = response.split(":\n");
        List<GroupName> unixGroups = new ArrayList<>();
        for (String group : groups) {
            String[] userValues = group.split(":");
            unixGroups.add(GroupName.of(userValues[0]));
        }
        return unixGroups;
    }
}
