package urchin.cli.folder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urchin.cli.Command;
import urchin.cli.common.BasicCommand;

import java.nio.file.Path;

@Component
public class ShareFolderCommand extends BasicCommand {

    private static final String FOLDER = "%folder%";
    private static final String FOLDER_NAME = "%folderName%";

    private final Command command;

    @Autowired
    public ShareFolderCommand(Runtime runtime, Command command) {
        super(runtime);
        this.command = command;
    }

    public void execute(Path folder) {
        LOG.info("Sharing folder {}", folder);
        executeCommand(command.getFolderCommand("share-folder")
                .replace(FOLDER_NAME, folder.getFileName().toString())
                .replace(FOLDER, folder.toAbsolutePath().toString())
        );
    }
}
