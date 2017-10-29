package urchin.cli.permission;

import org.springframework.stereotype.Component;
import urchin.cli.Command;
import urchin.cli.common.BasicCommand;
import urchin.model.folder.FileModes;

import java.nio.file.Path;

@Component
public class ChangeFileModesCommand extends BasicCommand {

    private static final String MODES = "%modes%";
    private static final String FILE = "%file%";

    private final Command command;

    protected ChangeFileModesCommand(Runtime runtime, Command command) {
        super(runtime);
        this.command = command;
    }

    public void execute(FileModes fileModes, Path file) {
        LOG.debug("Change file mode {}", fileModes);
        executeCommand(command.getPermissionCommand("change-file-modes")
                .replace(MODES, fileModes.getModes())
                .replace(FILE, file.toAbsolutePath().toString())
        );
    }
}