package urchin.cli.permission;

import org.springframework.stereotype.Component;
import urchin.cli.BasicCommand;
import urchin.cli.Command;
import urchin.model.group.GroupName;
import urchin.model.user.Username;

import java.nio.file.Path;

@Component
public class ChangeOwnerCommand extends BasicCommand {

    private static final String OWNER = "%owner%";
    private static final String GROUP = "%group%";
    private static final String FILE = "%file%";
    private static final String CHANGE_OWNER = "change-owner";

    private final Command command;

    protected ChangeOwnerCommand(Runtime runtime, Command command) {
        super(runtime);
        this.command = command;
    }

    public void execute(Path file, Username username, GroupName groupName) {
        log.info("Changing owner of file {} to {}:{}", file, username, groupName);
        executeCommand(command.getPermissionCommand(CHANGE_OWNER)
                .replace(OWNER, username.getValue())
                .replace(GROUP, groupName.getValue())
                .replace(FILE, file.toAbsolutePath().toString())
        );
    }
}
