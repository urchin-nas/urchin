package urchin.cli.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urchin.cli.common.BasicCommand;
import urchin.cli.common.CommandException;
import urchin.model.Username;

import static java.util.Arrays.copyOf;

@Component
public class CheckIfUsernameExistCommand extends BasicCommand {

    private static final String USERNAME = "%username%";

    private static final String[] COMMAND = new String[]{
            "getent",
            "passwd",
            USERNAME,
    };

    @Autowired
    public CheckIfUsernameExistCommand(Runtime runtime) {
        super(runtime);
    }

    public boolean execute(Username username) {
        LOG.debug("Checking if username {} exist", username);
        try {
            executeCommand(setupCommand(username));
        } catch (CommandException e) {
            if (e.getExitValue() == 2) {
                return false;
            } else {
                throw e;
            }
        }
        return true;
    }

    private String[] setupCommand(Username username) {
        String[] command = copyOf(COMMAND, COMMAND.length);
        command[2] = username.getValue();
        return command;
    }
}
