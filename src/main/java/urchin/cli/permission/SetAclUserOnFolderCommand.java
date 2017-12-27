package urchin.cli.permission;

import org.springframework.stereotype.Component;
import urchin.cli.BasicCommand;
import urchin.cli.Command;
import urchin.model.permission.AclPermission;
import urchin.model.user.Username;

import java.nio.file.Path;

@Component
public class SetAclUserOnFolderCommand extends BasicCommand {

    private final Command command;

    protected SetAclUserOnFolderCommand(Runtime runtime, Command command) {
        super(runtime);
        this.command = command;
    }

    public void execute(Path folder, Username username, AclPermission aclPermission) {
        log.debug("Setting ACL permissions {} on {} for user {}", aclPermission, folder, username);

        executeCommand(command.getPermissionCommand("set-acl-user-on-folder")
                .replace("%folder%", folder.toAbsolutePath().toString())
                .replace("%user%", username.getValue())
                .replace("%permission%", aclPermission.getPermissions())
        );

    }
}
