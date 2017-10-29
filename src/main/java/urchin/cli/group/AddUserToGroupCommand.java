package urchin.cli.group;


import org.springframework.stereotype.Component;
import urchin.cli.Command;
import urchin.cli.common.BasicCommand;
import urchin.model.group.Group;
import urchin.model.user.User;

@Component
public class AddUserToGroupCommand extends BasicCommand {

    private static final String USERNAME = "%username%";
    private static final String GROUP = "%group%";

    private final Command command;

    public AddUserToGroupCommand(Runtime runtime, Command command) {
        super(runtime);
        this.command = command;
    }

    public void execute(User user, Group group) {
        LOG.info("Adding user {} to group {}", user, group);
        executeCommand(command.getGroupCommand("add-user-to-group")
                .replace(USERNAME, user.getUsername().getValue())
                .replace(GROUP, group.getName().getValue())
        );
    }
}