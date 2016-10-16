package urchin.domain.cli.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urchin.domain.cli.BasicCommand;
import urchin.domain.model.User;

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

    public void execute(User user) {
        LOG.debug("Removing user {}", user.getUsername());
        executeCommand(setupCommand(user));
    }

    private String[] setupCommand(User user) {
        String[] command = copyOf(COMMAND, COMMAND.length);
        command[2] = user.getUsername();
        return command;
    }
}
