package urchin.cli.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urchin.cli.BasicCommand;
import urchin.cli.CommandException;

import static java.util.Arrays.copyOf;

@Component
public class CheckIfGroupExistCommand extends BasicCommand {

    private static final String GROUP_NAME = "%groupname%";

    private static final String[] COMMAND = new String[]{
            "getent",
            "group",
            GROUP_NAME,
    };

    @Autowired
    public CheckIfGroupExistCommand(Runtime runtime) {
        super(runtime);
    }

    public boolean execute(String groupName) {
        LOG.debug("Checking if group name {} exist", groupName);
        try {
            executeCommand(setupCommand(groupName));
        } catch (CommandException e) {
            if (e.getExitValue() == 2) {
                return false;
            } else {
                throw e;
            }
        }
        return true;
    }

    private String[] setupCommand(String groupName) {
        String[] command = copyOf(COMMAND, COMMAND.length);
        command[2] = groupName;
        return command;
    }
}
