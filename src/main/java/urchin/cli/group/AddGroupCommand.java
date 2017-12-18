package urchin.cli.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urchin.cli.BasicCommand;
import urchin.cli.Command;
import urchin.model.group.GroupName;

@Component
public class AddGroupCommand extends BasicCommand {

    private static final String GROUP = "%group%";
    private static final String ADD_GROUP = "add-group";

    private final Command command;

    @Autowired
    public AddGroupCommand(Runtime runtime, Command command) {
        super(runtime);
        this.command = command;
    }

    public void execute(GroupName groupName) {
        log.debug("Creating group {}", groupName);
        executeCommand(command.getGroupCommand(ADD_GROUP)
                .replace(GROUP, groupName.getValue()));
    }
}
