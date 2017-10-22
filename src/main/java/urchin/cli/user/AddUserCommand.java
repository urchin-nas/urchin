package urchin.cli.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urchin.cli.Command;
import urchin.cli.common.BasicCommand;
import urchin.model.user.Username;

@Component
public class AddUserCommand extends BasicCommand {

    private static final String USERNAME = "%username%";

    private final Command command;

    @Autowired
    public AddUserCommand(Runtime runtime, Command command) {
        super(runtime);
        this.command = command;
    }

    public void execute(Username username) {
        LOG.debug("Creating user {}", username);
        executeCommand(command.getUserCommand("add-user").replace(USERNAME, username.getValue()));
    }
}
