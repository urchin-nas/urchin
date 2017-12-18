package urchin.cli.folder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urchin.cli.BasicCommand;
import urchin.cli.Command;
import urchin.model.folder.Folder;

@Component
public class ShareFolderCommand extends BasicCommand {

    private static final String FOLDER = "%folder%";
    private static final String FOLDER_NAME = "%folderName%";
    private static final String SHARE_FOLDER = "share-folder";

    private final Command command;

    @Autowired
    public ShareFolderCommand(Runtime runtime, Command command) {
        super(runtime);
        this.command = command;
    }

    public void execute(Folder folder) {
        log.info("Sharing folder {}", folder);
        executeCommand(command.getFolderCommand(SHARE_FOLDER)
                .replace(FOLDER_NAME, folder.getPath().getFileName().toString())
                .replace(FOLDER, folder.toAbsolutePath())
        );
    }
}
