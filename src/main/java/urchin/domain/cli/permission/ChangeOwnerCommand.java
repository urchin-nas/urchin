package urchin.domain.cli.permission;

import org.springframework.stereotype.Component;
import urchin.domain.cli.common.BasicCommand;
import urchin.domain.model.Group;
import urchin.domain.model.User;

import java.nio.file.Path;

import static java.util.Arrays.copyOf;

@Component
public class ChangeOwnerCommand extends BasicCommand {

    private static final String OWNER = "%OWNER%";
    private static final String GROUP = "%GROUP%";
    private static final String OWNER_AND_GROUP = OWNER + ":" + GROUP;
    private static final String FILE = "%FILE%";

    private static final String[] COMMAND = new String[]{
            "chown",
            "-R",
            OWNER_AND_GROUP,
            FILE
    };

    protected ChangeOwnerCommand(Runtime runtime) {
        super(runtime);
    }

    public void execute(User user, Group group, Path file) {
        LOG.debug("Change owner of file {} to {}:{}", file, user, group);
        executeCommand(setupCommand(user, group, file));
    }

    private String[] setupCommand(User user, Group group, Path file) {
        String[] command = copyOf(COMMAND, COMMAND.length);
        command[2] = String.format("%s:%s", user.getUsername(), group.getName());
        command[3] = file.toAbsolutePath().toString();
        return command;
    }
}
