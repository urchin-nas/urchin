package urchin.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import urchin.cli.permission.*;
import urchin.model.group.GroupName;
import urchin.model.permission.*;
import urchin.model.user.LinuxUser;
import urchin.model.user.Username;

import java.nio.file.Path;

@Repository
public class PermissionCli {

    private final ChangeFileModesCommand changeFileModesCommand;
    private final ChangeOwnerCommand changeOwnerCommand;
    private final ListFileInformationCommand listFileInformationCommand;
    private final GetAclCommand getAclCommand;
    private final SetAclGroupOnFileCommand setAclGroupOnFileCommand;
    private final SetAclGroupOnFolderCommand setAclGroupOnFolderCommand;
    private final SetAclUserOnFileCommand setAclUserOnFileCommand;
    private final SetAclUserOnFolderCommand setAclUserOnFolderCommand;

    @Autowired
    public PermissionCli(
            ChangeFileModesCommand changeFileModesCommand,
            ChangeOwnerCommand changeOwnerCommand,
            ListFileInformationCommand listFileInformationCommand,
            GetAclCommand getAclCommand,
            SetAclGroupOnFileCommand setAclGroupOnFileCommand,
            SetAclGroupOnFolderCommand setAclGroupOnFolderCommand,
            SetAclUserOnFileCommand setAclUserOnFileCommand,
            SetAclUserOnFolderCommand setAclUserOnFolderCommand) {
        this.changeFileModesCommand = changeFileModesCommand;
        this.changeOwnerCommand = changeOwnerCommand;
        this.listFileInformationCommand = listFileInformationCommand;
        this.getAclCommand = getAclCommand;
        this.setAclGroupOnFileCommand = setAclGroupOnFileCommand;
        this.setAclGroupOnFolderCommand = setAclGroupOnFolderCommand;
        this.setAclUserOnFileCommand = setAclUserOnFileCommand;
        this.setAclUserOnFolderCommand = setAclUserOnFolderCommand;
    }

    public void changeFileMode(FileModes fileModes, Path file) {
        changeFileModesCommand.execute(fileModes, file);
    }

    public void changeOwner(Path file, Username username, GroupName groupName) {
        changeOwnerCommand.execute(file, username, groupName);
    }

    public void changeOwner(Path file, LinuxUser linuxUser) {
        changeOwnerCommand.execute(file, linuxUser.getUsername(), GroupName.of(linuxUser.getUsername().getValue()));
    }

    public FileModes getFileModes(Path file) {
        return listFileInformationCommand.execute(file)
                .map(s -> ImmutableFileModes.from(s.split(" ")[0]))
                .orElse(null);
    }

    public FileOwners getFileOwners(Path file) {
        return listFileInformationCommand.execute(file)
                .map(s -> {
                    String[] split = s.split(" ");
                    return ImmutableFileOwners.builder()
                            .user(split[2])
                            .group(split[3])
                            .build();
                })
                .orElse(null);
    }

    public Acl getAcl(Path path) {
        return getAclCommand.execute(path);
    }

    public void setAclGroupPermissions(Path path, GroupName groupName, AclPermission aclPermission) {
        if (path.toFile().isDirectory()) {
            setAclGroupOnFolderCommand.execute(path, groupName, aclPermission);
        } else {
            setAclGroupOnFileCommand.execute(path, groupName, aclPermission);
        }
    }

    public void setAclUserPermissions(Path path, Username username, AclPermission aclPermission) {
        if (path.toFile().isDirectory()) {
            setAclUserOnFolderCommand.execute(path, username, aclPermission);
        } else {
            setAclUserOnFileCommand.execute(path, username, aclPermission);
        }
    }
}
