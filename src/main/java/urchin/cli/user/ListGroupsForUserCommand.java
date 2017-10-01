package urchin.cli.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urchin.cli.common.BasicCommand;
import urchin.model.User;

import java.util.Optional;

import static java.util.Arrays.copyOf;

@Component
public class ListGroupsForUserCommand extends BasicCommand {

    private static final String USER = "%user%";

    private static final String[] COMMAND = new String[]{
            "groups",
            USER,
    };

    @Autowired
    public ListGroupsForUserCommand(Runtime runtime) {
        super(runtime);
    }

    public Optional<String> execute(User user) {
        return executeCommand(setupCommand(user));
    }

    private String[] setupCommand(User user) {
        String[] command = copyOf(COMMAND, COMMAND.length);
        command[1] = user.getUsername().getValue();
        return command;
    }
}
