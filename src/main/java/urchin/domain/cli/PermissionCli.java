package urchin.domain.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import urchin.domain.cli.permission.ChangeFileModesCommand;
import urchin.domain.cli.permission.ChangeOwnerCommand;
import urchin.domain.cli.permission.ListFileInformationCommand;
import urchin.domain.model.FileModes;
import urchin.domain.model.FileOwners;
import urchin.domain.model.Group;
import urchin.domain.model.User;

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

    public void changeOwner(User user, Group group, Path file) {
        changeOwnerCommand.execute(user, group, file);
    }

    public FileModes getFileModes(Path file) {
        return new FileModes(listFileInformationCommand.execute(file).get().split(" ")[0]);
    }

    public FileOwners getFileOwners(Path file) {
        String[] split = listFileInformationCommand.execute(file).get().split(" ");
        return new FileOwners(split[2], split[3]);
    }
}
