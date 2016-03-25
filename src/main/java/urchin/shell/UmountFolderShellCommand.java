package urchin.shell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.Arrays;

public class UmountFolderShellCommand {

    public static final String FOLDER = "%folder%";
    private static final Logger LOG = LoggerFactory.getLogger(MountVirtualFolderShellCommand.class);

    private static final String[] COMMAND = new String[]{"sudo", "umount", "-l", FOLDER};

    private final Runtime runtime;

    @Autowired
    public UmountFolderShellCommand(Runtime runtime) {
        this.runtime = runtime;
    }

    public void execute(File folder) {
        LOG.debug("Unmounting folder {}", folder.getAbsoluteFile());
        String[] command = Arrays.copyOf(COMMAND, COMMAND.length);
        command[3] = folder.getAbsolutePath();
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
