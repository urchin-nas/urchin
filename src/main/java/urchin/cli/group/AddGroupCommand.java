package urchin.cli.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urchin.cli.common.BasicCommand;

import static java.util.Arrays.copyOf;

@Component
public class AddGroupCommand extends BasicCommand {

    private static final String GROUP = "%group%";

    private static final String[] COMMAND = new String[]{
            "sudo",
            "addgroup",
            GROUP,
    };

    @Autowired
    public AddGroupCommand(Runtime runtime) {
        super(runtime);
    }

    public void execute(String groupName) {
        LOG.debug("Creating group {}", groupName);
        executeCommand(setupCommand(groupName));
    }

    private String[] setupCommand(String groupName) {
        String[] command = copyOf(COMMAND, COMMAND.length);
        command[2] = groupName;
        return command;
    }
}
