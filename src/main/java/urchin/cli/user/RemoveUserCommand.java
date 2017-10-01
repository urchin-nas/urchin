package urchin.cli.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urchin.cli.common.BasicCommand;
import urchin.model.user.Username;

import static java.util.Arrays.copyOf;

@Component
public class RemoveUserCommand extends BasicCommand {

    private static final String USERNAME = "%username%";

    private static final String[] COMMAND = new String[]{
            "sudo",
            "deluser",
            USERNAME,
    };

    @Autowired
    public RemoveUserCommand(Runtime runtime) {
        super(runtime);
    }

    public void execute(Username username) {
        LOG.debug("Removing user {}", username);
        executeCommand(setupCommand(username));
    }

    private String[] setupCommand(Username username) {
        String[] command = copyOf(COMMAND, COMMAND.length);
        command[2] = username.getValue();
        return command;
    }
}
