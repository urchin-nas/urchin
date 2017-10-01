package urchin.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import urchin.cli.permission.ChangeFileModesCommand;
import urchin.cli.permission.ChangeOwnerCommand;
import urchin.cli.permission.ListFileInformationCommand;
import urchin.model.FileModes;
import urchin.model.FileOwners;
import urchin.model.ImmutableFileModes;
import urchin.model.ImmutableFileOwners;

import java.nio.file.Path;

@Repository
public class PermissionCli {

    private final ChangeFileModesCommand changeFileModesCommand;
    private final ChangeOwnerCommand changeOwnerCommand;
    private final ListFileInformationCommand listFileInformationCommand;

    @Autowired
    public PermissionCli(
            ChangeFileModesCommand changeFileModesCommand,
            ChangeOwnerCommand changeOwnerCommand,
            ListFileInformationCommand listFileInformationCommand) {
        this.changeFileModesCommand = changeFileModesCommand;
        this.changeOwnerCommand = changeOwnerCommand;
        this.listFileInformationCommand = listFileInformationCommand;
    }

    public void changeFileMode(FileModes fileModes, Path file) {
        changeFileModesCommand.execute(fileModes, file);
    }

    public void changeOwner(Path file, String username, String groupName) {
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
}
