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
        String cmd = command.getFolderCommand(SET_FOLDER_IMMUTABLE);
        try {
            executeCommand(cmd.replace(FOLDER, folder.toAbsolutePath()));
        } catch (Exception e) {
            log.warn("Could not set folder {} to immutable. {} is probably not supported by the file system. Continuing...", folder, cmd, e);
        }
    }
}
