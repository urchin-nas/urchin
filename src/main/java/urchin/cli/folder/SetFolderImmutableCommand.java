package urchin.cli.folder;

import org.springframework.stereotype.Component;
import urchin.cli.BasicCommand;
import urchin.cli.Command;
import urchin.model.folder.FolderWrapper;

@Component
public class SetFolderImmutableCommand extends BasicCommand {

    private static final String FOLDER = "%folder%";
    private static final String SET_FOLDER_IMMUTABLE = "set-folder-immutable";
    private final Command command;

    protected SetFolderImmutableCommand(Runtime runtime, Command command) {
        super(runtime);
        this.command = command;
    }

    public void execute(FolderWrapper folder) {
        log.info("Setting folder {} immutable", folder);
        executeCommand(command.getFolderCommand(SET_FOLDER_IMMUTABLE)
                .replace(FOLDER, folder.toAbsolutePath())
        );
    }
}
