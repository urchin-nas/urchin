package urchin.cli.folder;

import org.springframework.stereotype.Component;
import urchin.cli.BasicCommand;
import urchin.cli.Command;
import urchin.model.folder.FolderWrapper;

@Component
public class RemoveFolderCommand extends BasicCommand {

    private static final String FOLDER = "%folder%";
    private static final String REMOVE_FOLDER = "remove-folder";
    private final Command command;

    protected RemoveFolderCommand(Runtime runtime, Command command) {
        super(runtime);
        this.command = command;
    }

    public void execute(FolderWrapper folder) {
        log.info("Removing folder {}", folder);
        executeCommand(command.getFolderCommand(REMOVE_FOLDER)
                .replace(FOLDER, folder.toAbsolutePath())
        );
    }
}
