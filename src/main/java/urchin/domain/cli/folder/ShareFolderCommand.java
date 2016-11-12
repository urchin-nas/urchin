package urchin.domain.cli.folder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urchin.domain.cli.common.BasicCommand;

import java.nio.file.Path;

import static java.util.Arrays.copyOf;

@Component
public class ShareFolderCommand extends BasicCommand {

    private static final String FOLDER_PATH = "%folderPath%";
    private static final String FOLDER_NAME = "%folderName%";

    private static final String[] COMMAND = {
            "sudo",
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
    public ShareFolderCommand(Runtime runtime) {
        super(runtime);
    }

    public void execute(Path folder) {
        LOG.info("Sharing folder {}", folder);
        executeCommand(setupCommand(folder));
    }

    private String[] setupCommand(Path folder) {
        String[] command = copyOf(COMMAND, COMMAND.length);
        command[4] = folder.getFileName().toString();
        command[5] = folder.toAbsolutePath().toString();
        return command;
    }

}
