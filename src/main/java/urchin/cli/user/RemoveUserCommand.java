package urchin.cli.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urchin.cli.Command;
import urchin.cli.common.BasicCommand;
import urchin.model.user.Username;

@Component
public class RemoveUserCommand extends BasicCommand {

    private static final String USERNAME = "%username%";

    private final Command command;

    @Autowired
    public RemoveUserCommand(Runtime runtime, Command command) {
        super(runtime);
        this.command = command;
    }

    public void execute(Username username) {
        LOG.debug("Removing user {}", username);
        executeCommand(command.getUserCommand("remove-user").replace(USERNAME, username.getValue()));
    }
}
