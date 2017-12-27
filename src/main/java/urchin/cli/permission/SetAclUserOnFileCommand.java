package urchin.cli.permission;

import org.springframework.stereotype.Component;
import urchin.cli.BasicCommand;
import urchin.cli.Command;
import urchin.model.permission.AclPermission;
import urchin.model.user.Username;

import java.nio.file.Path;

@Component
public class SetAclUserOnFileCommand extends BasicCommand {

    private final Command command;

    protected SetAclUserOnFileCommand(Runtime runtime, Command command) {
        super(runtime);
        this.command = command;
    }

    public void execute(Path file, Username username, AclPermission aclPermission) {
        log.debug("Setting ACL permissions {} on {} for user {}", aclPermission, file, username);

        executeCommand(command.getPermissionCommand("set-acl-user-on-file")
                .replace("%file%", file.toAbsolutePath().toString())
                .replace("%user%", username.getValue())
                .replace("%permission%", aclPermission.getPermissions())
        );

    }
}
