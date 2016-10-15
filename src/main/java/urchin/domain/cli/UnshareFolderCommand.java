package urchin.domain.cli;

import org.springframework.stereotype.Component;

import java.nio.file.Path;

import static java.util.Arrays.copyOf;

@Component
public class UnshareFolderCommand extends BasicCommand {

    private static final String FOLDER_NAME = "%folderName%";

    private static final String[] COMMAND = {
            "sudo",
            "net",
            "usershare",
            "delete",
            FOLDER_NAME
    };

    public UnshareFolderCommand(Runtime runtime) {
        super(runtime);
    }

    public void execute(Path folder) {
        LOG.info("Unsharing folder {}", folder);
        executeCommand(setupCommand(folder));
    }

    private String[] setupCommand(Path folder) {
        String[] command = copyOf(COMMAND, COMMAND.length);
        command[4] = folder.getFileName().toString();
        return command;
    }
}
