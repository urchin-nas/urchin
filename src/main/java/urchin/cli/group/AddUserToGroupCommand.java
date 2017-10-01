package urchin.cli.group;


import org.springframework.stereotype.Component;
import urchin.cli.common.BasicCommand;
import urchin.model.Group;
import urchin.model.User;

import static java.util.Arrays.copyOf;

@Component
public class AddUserToGroupCommand extends BasicCommand {

    private static final String USERNAME = "%username%";
    private static final String GROUP = "%group%";

    private static final String[] COMMAND = new String[]{
            "sudo",
            "adduser",
            USERNAME,
            GROUP
    };

    public AddUserToGroupCommand(Runtime runtime) {
        super(runtime);
    }

    public void execute(User user, Group group) {
        LOG.info("Adding user {} to group {}", user, group);
        executeCommand(setupCommand(user, group));
    }

    private String[] setupCommand(User user, Group group) {
        String[] command = copyOf(COMMAND, COMMAND.length);
        command[2] = user.getUsername();
        command[3] = group.getName();
        return command;
    }
}
