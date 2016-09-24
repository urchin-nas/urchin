package urchin.domain.cli;

import org.springframework.beans.factory.annotation.Autowired;

import java.nio.file.Path;

import static java.util.Arrays.copyOf;

public class ShareFolderCommand extends Command {

    private static final String FOLDER_PATH = "%folderPath%";
    private static final String FOLDER_NAME = "%folderName%";

    private static final String[] COMMAND = {
            "net",
            "usershare",
            "add",
            FOLDER_NAME,
            FOLDER_PATH,
            "desc",
            "everyone:F",
            "guest_ok=y"
    };

    @Autowired
    protected ShareFolderCommand(Runtime runtime) {
        super(runtime);
    }

    public void execute(Path folder) {
        LOG.info("Sharing folder {}", folder);
        executeCommand(setupCommand(folder));
    }

    private String[] setupCommand(Path folder) {
        String[] command = copyOf(COMMAND, COMMAND.length);
        command[3] = folder.getFileName().toString();
        command[4] = folder.toAbsolutePath().toString();
        return command;
    }

}
