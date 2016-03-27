package urchin.shell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.springframework.util.StringUtils.arrayToDelimitedString;

@Repository
public class MountVirtualFolderShellCommand {

    private static final Logger LOG = LoggerFactory.getLogger(MountVirtualFolderShellCommand.class);
    public static final String FOLDER_LIST = "%folderList%";
    public static final String VIRTUAL_FOLDER_PATH = "%virtualFolderPath%";

    private static final String[] COMMAND = new String[]{"mhddfs", "-o", "allow_other", FOLDER_LIST, VIRTUAL_FOLDER_PATH};

    private final Runtime runtime;

    @Autowired
    public MountVirtualFolderShellCommand(Runtime runtime) {
        this.runtime = runtime;
    }

    public void execute(List<Path> folders, Path virtualFolder) {
        LOG.debug("Mounting virtual folder {} for {} folders", virtualFolder.toAbsolutePath(), folders.size());
        String[] command = Arrays.copyOf(COMMAND, COMMAND.length);
        command[3] = arrayToDelimitedString(folders.toArray(), ",");
        command[4] = virtualFolder.toAbsolutePath().toString();
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
