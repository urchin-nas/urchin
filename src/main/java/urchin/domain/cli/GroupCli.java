package urchin.domain.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import urchin.domain.cli.group.AddGroupCommand;
import urchin.domain.cli.group.CheckIfGroupExistCommand;
import urchin.domain.cli.group.RemoveGroupCommand;
import urchin.domain.model.Group;

@Repository
public class GroupCli {

    private final AddGroupCommand addGroupCommand;
    private final CheckIfGroupExistCommand checkIfGroupExistCommand;
    private final RemoveGroupCommand removeGroupCommand;

    @Autowired
    public GroupCli(AddGroupCommand addGroupCommand, CheckIfGroupExistCommand checkIfGroupExistCommand, RemoveGroupCommand removeGroupCommand) {
        this.addGroupCommand = addGroupCommand;
        this.checkIfGroupExistCommand = checkIfGroupExistCommand;
        this.removeGroupCommand = removeGroupCommand;
    }

    public void addGroup(Group group) {
        addGroupCommand.execute(group);
    }

    public boolean checkIfGroupExist(String groupName) {
        return checkIfGroupExistCommand.execute(groupName);
    }

    public void removeGroup(Group group) {
        removeGroupCommand.execute(group);
    }
}
