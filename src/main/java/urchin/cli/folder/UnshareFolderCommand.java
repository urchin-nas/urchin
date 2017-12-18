package urchin.cli.folder;

import org.springframework.stereotype.Component;
import urchin.cli.BasicCommand;
import urchin.cli.Command;
import urchin.model.folder.Folder;

@Component
public class UnshareFolderCommand extends BasicCommand {

    private static final String FOLDER = "%folder%";
    private static final String UNSHARE_FOLDER = "unshare-folder";

    private final Command command;

    public UnshareFolderCommand(Runtime runtime, Command command) {
        super(runtime);
        this.command = command;
    }

    public void execute(Folder folder) {
        log.info("Unsharing folder {}", folder);
        executeCommand(command.getFolderCommand(UNSHARE_FOLDER)
                .replace(FOLDER, folder.getPath().getFileName().toString())
        );
    }
}
