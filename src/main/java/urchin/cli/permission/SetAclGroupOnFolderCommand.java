package urchin.cli.permission;

import org.springframework.stereotype.Component;
import urchin.cli.BasicCommand;
import urchin.cli.Command;
import urchin.model.group.GroupName;
import urchin.model.permission.AclPermission;

import java.nio.file.Path;

@Component
public class SetAclGroupOnFolderCommand extends BasicCommand {

    private final Command command;

    protected SetAclGroupOnFolderCommand(Runtime runtime, Command command) {
        super(runtime);
        this.command = command;
    }

    public void execute(Path folder, GroupName groupName, AclPermission aclPermission) {
        log.debug("Setting ACL permissions {} on {} for group {}", aclPermission, folder, groupName);

        executeCommand(command.getPermissionCommand("set-acl-group-on-folder")
                .replace("%folder%", folder.toAbsolutePath().toString())
                .replace("%group%", groupName.getValue())
                .replace("%permission%", aclPermission.getPermissions())
        );

    }
}
