package urchin.cli.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urchin.cli.Command;
import urchin.cli.common.BasicCommand;
import urchin.model.group.GroupName;

@Component
public class RemoveGroupCommand extends BasicCommand {

    private static final String GROUP = "%group%";

    private final Command command;

    @Autowired
    public RemoveGroupCommand(Runtime runtime, Command command) {
        super(runtime);
        this.command = command;
    }

    public void execute(GroupName groupName) {
        LOG.debug("Removing group {}", groupName);
        executeCommand(command.getGroupCommand("remove-group").replace(GROUP, groupName.getValue()));
    }
}
