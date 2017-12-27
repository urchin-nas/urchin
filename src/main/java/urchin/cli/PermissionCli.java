package urchin.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import urchin.cli.permission.*;
import urchin.model.group.GroupName;
import urchin.model.permission.*;
import urchin.model.user.Username;

import java.nio.file.Files;
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

    public FileModes getFileModes(Path file) {
        return ImmutableFileModes.from(listFileInformationCommand.execute(file).get().split(" ")[0]);
    }

    public FileOwners getFileOwners(Path file) {
        String[] split = listFileInformationCommand.execute(file).get().split(" ");
        return ImmutableFileOwners.builder()
                .user(split[2])
                .group(split[3])
                .build();
    }

    public Acl getAcl(Path path) {
        return getAclCommand.execute(path);
    }

    public void setAclGroupPermissions(Path path, GroupName groupName, AclPermission aclPermission) {
        if (Files.isDirectory(path)) {
            setAclGroupOnFolderCommand.execute(path, groupName, aclPermission);
        } else {
            setAclGroupOnFileCommand.execute(path, groupName, aclPermission);
        }
    }

    public void setAclUserPermissions(Path path, Username username, AclPermission aclPermission) {
        if (Files.isDirectory(path)) {
            setAclUserOnFolderCommand.execute(path, username, aclPermission);
        } else {
            setAclUserOnFileCommand.execute(path, username, aclPermission);
        }
    }
}
