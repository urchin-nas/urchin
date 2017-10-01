package urchin.cli.permission;

import org.springframework.stereotype.Component;
import urchin.cli.common.BasicCommand;
import urchin.model.group.GroupName;
import urchin.model.user.Username;

import java.nio.file.Path;

import static java.util.Arrays.copyOf;

@Component
public class ChangeOwnerCommand extends BasicCommand {

    private static final String OWNER = "%OWNER%";
    private static final String GROUP = "%GROUP%";
    private static final String OWNER_AND_GROUP = OWNER + ":" + GROUP;
    private static final String FILE = "%FILE%";

    private static final String[] COMMAND = new String[]{
            "sudo",
            "chown",
            "-R",
            OWNER_AND_GROUP,
            FILE
    };

    protected ChangeOwnerCommand(Runtime runtime) {
        super(runtime);
    }

    public void execute(Path file, Username username, GroupName groupName) {
        LOG.debug("Change owner of file {} to {}:{}", file, username, groupName);
        executeCommand(setupCommand(file, username, groupName));
    }

    private String[] setupCommand(Path file, Username username, GroupName groupName) {
        String[] command = copyOf(COMMAND, COMMAND.length);
        command[3] = String.format("%s:%s", username.getValue(), groupName.getValue());
        command[4] = file.toAbsolutePath().toString();
        return command;
    }
}
