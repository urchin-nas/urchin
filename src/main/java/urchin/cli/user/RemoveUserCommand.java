package urchin.cli.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urchin.cli.BasicCommand;
import urchin.cli.Command;
import urchin.model.user.Username;

@Component
public class RemoveUserCommand extends BasicCommand {

    private static final String USERNAME = "%username%";
    private static final String REMOVE_USER = "remove-user";

    private final Command command;

    @Autowired
    public RemoveUserCommand(Runtime runtime, Command command) {
        super(runtime);
        this.command = command;
    }

    public void execute(Username username) {
        log.debug("Removing user {}", username);
        executeCommand(command.getUserCommand(REMOVE_USER)
                .replace(USERNAME, username.getValue()));
    }
}
