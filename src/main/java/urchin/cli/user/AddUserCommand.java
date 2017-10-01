package urchin.cli.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urchin.cli.common.BasicCommand;
import urchin.model.user.Username;

import static java.util.Arrays.copyOf;

@Component
public class AddUserCommand extends BasicCommand {

    private static final String USERNAME = "%username%";

    private static final String[] COMMAND = new String[]{
            "sudo",
            "useradd",
            USERNAME,
    };

    @Autowired
    public AddUserCommand(Runtime runtime) {
        super(runtime);
    }

    public void execute(Username username) {
        LOG.debug("Creating user {}", username);
        executeCommand(setupCommand(username));
    }

    private String[] setupCommand(Username username) {
        String[] command = copyOf(COMMAND, COMMAND.length);
        command[2] = username.getValue();
        return command;
    }
}
