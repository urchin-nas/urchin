package urchin.cli.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urchin.cli.BasicCommand;
import urchin.cli.Command;
import urchin.model.user.Username;

@Component
public class AddUserCommand extends BasicCommand {

    private static final String USERNAME = "%username%";
    private static final String ADD_USER = "add-user";

    private final Command command;

    @Autowired
    public AddUserCommand(Runtime runtime, Command command) {
        super(runtime);
        this.command = command;
    }

    public void execute(Username username) {
        log.debug("Creating user {}", username);
        executeCommand(command.getUserCommand(ADD_USER)
                .replace(USERNAME, username.getValue()));
    }
}
