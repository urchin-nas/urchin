package urchin.cli.folder;

import org.springframework.stereotype.Component;
import urchin.cli.BasicCommand;
import urchin.cli.Command;
import urchin.model.folder.FolderWrapper;

@Component
public class SetFolderMutableCommand extends BasicCommand {

    private static final String FOLDER = "%folder%";
    private static final String SET_FOLDER_MUTABLE = "set-folder-mutable";
    private final Command command;

    protected SetFolderMutableCommand(Runtime runtime, Command command) {
        super(runtime);
        this.command = command;
    }

    public void execute(FolderWrapper folder) {
        log.info("Setting folder {} mutable", folder);
        executeCommand(command.getFolderCommand(SET_FOLDER_MUTABLE)
                .replace(FOLDER, folder.toAbsolutePath())
        );
    }
}
