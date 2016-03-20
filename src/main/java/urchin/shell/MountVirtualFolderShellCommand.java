package urchin.shell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;

import static org.springframework.util.StringUtils.arrayToDelimitedString;

public class MountVirtualFolderShellCommand {

    private static final Logger LOG = LoggerFactory.getLogger(MountVirtualFolderShellCommand.class);
    public static final String FOLDER_LIST = "%folderList%";
    public static final String DESTINATION_FOLDER_PATH = "%destinationFolderPath%";

    private static final String[] COMMAND = new String[]{"mhddfs", FOLDER_LIST, DESTINATION_FOLDER_PATH};

    private final Runtime runtime;

    public MountVirtualFolderShellCommand(Runtime runtime) {
        this.runtime = runtime;
    }

    public void execute(List<String> folderPaths, String destinationFolderPath) {
        String[] command = Arrays.copyOf(COMMAND, COMMAND.length);
        command[1] = arrayToDelimitedString(folderPaths.toArray(), ",");
        command[2] = destinationFolderPath;
        try {
            Process process = runtime.exec(command);
            OutputStream outputStream = process.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            bufferedWriter.newLine();
            bufferedWriter.flush();
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
