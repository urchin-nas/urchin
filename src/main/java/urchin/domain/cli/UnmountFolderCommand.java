package urchin.domain.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.nio.file.Path;

import static java.util.Arrays.copyOf;

@Repository
public class UnmountFolderCommand {

    public static final String FOLDER = "%folder%";
    private static final Logger LOG = LoggerFactory.getLogger(UnmountFolderCommand.class);

    private static final String[] COMMAND = new String[]{"sudo", "umount", "-l", FOLDER};

    private final Runtime runtime;

    @Autowired
    public UnmountFolderCommand(Runtime runtime) {
        this.runtime = runtime;
    }

    public void execute(Path folder) {
        LOG.debug("Unmounting folder {}", folder.toAbsolutePath());
        String[] command = setupCommand(folder);
        try {
            Process process = runtime.exec(command);
            process.waitFor();
            if (process.exitValue() != 0) {
                throw new CommandException("Process returned code: " + process.exitValue());
            }
        } catch (Exception e) {
            LOG.error("Failed to execute command");
            throw new CommandException(e);
        }
    }

    private String[] setupCommand(Path folder) {
        String[] command = copyOf(COMMAND, COMMAND.length);
        command[3] = folder.toAbsolutePath().toString();
        return command;
    }

}
