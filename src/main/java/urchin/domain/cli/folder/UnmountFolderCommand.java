package urchin.domain.cli.folder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urchin.domain.cli.common.BasicCommand;

import java.nio.file.Path;

import static java.util.Arrays.copyOf;

@Component
public class UnmountFolderCommand extends BasicCommand {

    private static final String FOLDER = "%folder%";

    private static final String[] COMMAND = new String[]{
            "umount",
            "-l",
            FOLDER
    };

    @Autowired
    public UnmountFolderCommand(Runtime runtime) {
        super(runtime);
    }

    public void execute(Path folder) {
        LOG.debug("Unmounting folder {}", folder.toAbsolutePath());
        executeCommand(setupCommand(folder));
    }

    private String[] setupCommand(Path folder) {
        String[] command = copyOf(COMMAND, COMMAND.length);
        command[2] = folder.toAbsolutePath().toString();
        return command;
    }

}
