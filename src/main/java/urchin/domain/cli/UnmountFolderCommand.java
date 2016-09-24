package urchin.domain.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.nio.file.Path;

import static java.util.Arrays.copyOf;

@Repository
public class UnmountFolderCommand extends Command {

    private static final String FOLDER = "%folder%";
    private static final Logger LOG = LoggerFactory.getLogger(UnmountFolderCommand.class);

    private static final String[] COMMAND = new String[]{
            "sudo",
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
        command[3] = folder.toAbsolutePath().toString();
        return command;
    }

}
