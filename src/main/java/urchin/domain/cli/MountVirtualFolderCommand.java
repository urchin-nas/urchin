package urchin.domain.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.nio.file.Path;
import java.util.List;

import static java.util.Arrays.copyOf;
import static org.springframework.util.StringUtils.arrayToDelimitedString;

@Repository
public class MountVirtualFolderCommand {

    private static final Logger LOG = LoggerFactory.getLogger(MountVirtualFolderCommand.class);
    public static final String FOLDER_LIST = "%folderList%";
    public static final String VIRTUAL_FOLDER_PATH = "%virtualFolderPath%";

    private static final String[] COMMAND = new String[]{"mhddfs", "-o", "allow_other", FOLDER_LIST, VIRTUAL_FOLDER_PATH};

    private final Runtime runtime;

    @Autowired
    public MountVirtualFolderCommand(Runtime runtime) {
        this.runtime = runtime;
    }

    public void execute(List<Path> folders, Path virtualFolder) {
        LOG.debug("Mounting virtual folder {} for {} folders", virtualFolder.toAbsolutePath(), folders.size());
        String[] command = setupCommand(folders, virtualFolder);
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

    private String[] setupCommand(List<Path> folders, Path virtualFolder) {
        String[] command = copyOf(COMMAND, COMMAND.length);
        command[3] = arrayToDelimitedString(folders.toArray(), ",");
        command[4] = virtualFolder.toAbsolutePath().toString();
        return command;
    }
}
