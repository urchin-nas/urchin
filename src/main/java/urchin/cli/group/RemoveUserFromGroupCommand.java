package urchin.cli.group;


import org.springframework.stereotype.Component;
import urchin.cli.BasicCommand;
import urchin.cli.Command;
import urchin.model.group.Group;
import urchin.model.user.User;

@Component
public class RemoveUserFromGroupCommand extends BasicCommand {

    private static final String USERNAME = "%username%";
    private static final String GROUP = "%group%";
    private static final String REMOVE_USER_FROM_GROUP = "remove-user-from-group";

    private final Command command;

    public RemoveUserFromGroupCommand(Runtime runtime, Command command) {
        super(runtime);
        this.command = command;
    }

    public void execute(User user, Group group) {
        log.info("Removing user {} to group {}", user, group);
        executeCommand(command.getGroupCommand(REMOVE_USER_FROM_GROUP)
                .replace(USERNAME, user.getUsername().getValue())
                .replace(GROUP, group.getName().getValue())
        );
    }
}
