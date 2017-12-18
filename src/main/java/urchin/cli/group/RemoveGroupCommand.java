package urchin.cli.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urchin.cli.BasicCommand;
import urchin.cli.Command;
import urchin.model.group.GroupName;

@Component
public class RemoveGroupCommand extends BasicCommand {

    private static final String GROUP = "%group%";
    private static final String REMOVE_GROUP = "remove-group";

    private final Command command;

    @Autowired
    public RemoveGroupCommand(Runtime runtime, Command command) {
        super(runtime);
        this.command = command;
    }

    public void execute(GroupName groupName) {
        log.debug("Removing group {}", groupName);
        executeCommand(command.getGroupCommand(REMOVE_GROUP)
                .replace(GROUP, groupName.getValue()));
    }
}
