package urchin.cli.permission;

import org.springframework.stereotype.Component;
import urchin.cli.BasicCommand;
import urchin.cli.Command;
import urchin.model.permission.FileModes;

import java.nio.file.Path;

@Component
public class ChangeFileModesCommand extends BasicCommand {

    private static final String MODES = "%modes%";
    private static final String FILE = "%file%";
    private static final String CHANGE_FILE_MODES = "change-file-modes";

    private final Command command;

    protected ChangeFileModesCommand(Runtime runtime, Command command) {
        super(runtime);
        this.command = command;
    }

    public void execute(FileModes fileModes, Path file) {
        log.debug("Change file mode {}", fileModes);
        executeCommand(command.getPermissionCommand(CHANGE_FILE_MODES)
                .replace(MODES, fileModes.getModes())
                .replace(FILE, file.toAbsolutePath().toString())
        );
    }
}
