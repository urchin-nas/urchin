package urchin.shell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.nio.file.Path;
import java.util.Arrays;

@Repository
public class UmountFolderShellCommand {

    public static final String FOLDER = "%folder%";
    private static final Logger LOG = LoggerFactory.getLogger(UmountFolderShellCommand.class);

    private static final String[] COMMAND = new String[]{"sudo", "umount", "-l", FOLDER};

    private final Runtime runtime;

    @Autowired
    public UmountFolderShellCommand(Runtime runtime) {
        this.runtime = runtime;
    }

    public void execute(Path folder) {
        LOG.debug("Unmounting folder {}", folder.toAbsolutePath());
        String[] command = Arrays.copyOf(COMMAND, COMMAND.length);
        command[3] = folder.toAbsolutePath().toString();
        try {
            Process process = runtime.exec(command);
            process.waitFor();
            if (process.exitValue() != 0) {
                throw new ShellCommandException("Process returned code: " + process.exitValue());
            }
        } catch (Exception e) {
            LOG.error("Failed to execute command");
            throw new ShellCommandException(e);
        }
    }

}
