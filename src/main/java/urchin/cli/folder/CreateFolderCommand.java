package urchin.cli.folder;

import org.springframework.stereotype.Component;
import urchin.cli.BasicCommand;
import urchin.cli.Command;
import urchin.model.folder.FolderWrapper;

@Component
public class CreateFolderCommand extends BasicCommand {

    private static final String FOLDER = "%folder%";
    private static final String CREATE_FOLDER = "create-folder";
    private final Command command;

    protected CreateFolderCommand(Runtime runtime, Command command) {
        super(runtime);
        this.command = command;
    }

    public void execute(FolderWrapper folder) {
        log.info("Creating folder {}", folder);
        executeCommand(command.getFolderCommand(CREATE_FOLDER)
                .replace(FOLDER, folder.toAbsolutePath())
        );
    }
}
