package urchin.shell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

import static org.springframework.util.StringUtils.arrayToDelimitedString;

public class MountVirtualFolderShellCommand {

    private static final Logger LOG = LoggerFactory.getLogger(MountVirtualFolderShellCommand.class);
    public static final String FOLDER_LIST = "%folderList%";
    public static final String VIRTUAL_FOLDER_PATH = "%virtualFolderPath%";

    private static final String[] COMMAND = new String[]{"mhddfs", "-o", "allow_other", FOLDER_LIST, VIRTUAL_FOLDER_PATH};

    private final Runtime runtime;

    public MountVirtualFolderShellCommand(Runtime runtime) {
        this.runtime = runtime;
    }

    public void execute(List<String> folderPaths, String virtualFolderPath) {
        LOG.debug("Mounting virtual folder {} for {} folders", virtualFolderPath, folderPaths.size());
        String[] command = Arrays.copyOf(COMMAND, COMMAND.length);
        command[3] = arrayToDelimitedString(folderPaths.toArray(), ",");
        command[4] = virtualFolderPath;
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
