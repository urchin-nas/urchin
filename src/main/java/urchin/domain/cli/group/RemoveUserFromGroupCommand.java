package urchin.domain.cli.group;


import org.springframework.stereotype.Component;
import urchin.domain.cli.common.BasicCommand;
import urchin.domain.model.Group;
import urchin.domain.model.User;

import static java.util.Arrays.copyOf;

@Component
public class RemoveUserFromGroupCommand extends BasicCommand {

    private static final String USERNAME = "%username%";
    private static final String GROUP = "%group%";


    private static final String[] COMMAND = new String[]{
            "sudo",
            "deluser",
            USERNAME,
            GROUP
    };

    public RemoveUserFromGroupCommand(Runtime runtime) {
        super(runtime);
    }

    public void execute(User user, Group group) {
        LOG.info("Removing user {} to group {}", user, group);
        executeCommand(setupCommand(user, group));
    }

    private String[] setupCommand(User user, Group group) {
        String[] command = copyOf(COMMAND, COMMAND.length);
        command[2] = user.getUsername();
        command[3] = group.getName();
        return command;
    }
}
