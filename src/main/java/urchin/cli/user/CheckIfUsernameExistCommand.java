package urchin.cli.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urchin.cli.BasicCommand;
import urchin.cli.Command;
import urchin.exception.CommandException;
import urchin.model.user.Username;

@Component
public class CheckIfUsernameExistCommand extends BasicCommand {

    private static final String USERNAME = "%username%";
    private static final String CHECK_IF_USERNAME_EXIST = "check-if-username-exist";

    private final Command command;

    @Autowired
    public CheckIfUsernameExistCommand(Runtime runtime, Command command) {
        super(runtime);
        this.command = command;
    }

    public boolean execute(Username username) {
        log.debug("Checking if username {} exist", username);
        try {
            executeCommand(command.getUserCommand(CHECK_IF_USERNAME_EXIST)
                    .replace(USERNAME, username.getValue()));
        } catch (CommandException e) {
            if (e.getExitValue() == 2) {
                return false;
            } else {
                throw e;
            }
        }
        return true;
    }
}
