package urchin.cli.group;


import org.springframework.stereotype.Component;
import urchin.cli.BasicCommand;
import urchin.cli.Command;
import urchin.model.group.Group;
import urchin.model.user.User;

@Component
public class AddUserToGroupCommand extends BasicCommand {

    private static final String USERNAME = "%username%";
    private static final String GROUP = "%group%";
    private static final String ADD_USER_TO_GROUP = "add-user-to-group";

    private final Command command;

    public AddUserToGroupCommand(Runtime runtime, Command command) {
        super(runtime);
        this.command = command;
    }

    public void execute(User user, Group group) {
        log.info("Adding user {} to group {}", user, group);
        executeCommand(command.getGroupCommand(ADD_USER_TO_GROUP)
                .replace(USERNAME, user.getUsername().getValue())
                .replace(GROUP, group.getName().getValue())
        );
    }
}
