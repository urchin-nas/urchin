package urchin.cli.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urchin.cli.common.BasicCommand;
import urchin.model.GroupName;

import static java.util.Arrays.copyOf;

@Component
public class RemoveGroupCommand extends BasicCommand {

    private static final String GROUP = "%group%";

    private static final String[] COMMAND = new String[]{
            "sudo",
            "delgroup",
            GROUP,
    };

    @Autowired
    public RemoveGroupCommand(Runtime runtime) {
        super(runtime);
    }

    public void execute(GroupName groupName) {
        LOG.debug("Removing group {}", groupName);
        executeCommand(setupCommand(groupName));
    }

    private String[] setupCommand(GroupName groupName) {
        String[] command = copyOf(COMMAND, COMMAND.length);
        command[2] = groupName.getValue();
        return command;
    }
}
