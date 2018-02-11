package urchin.cli.folder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urchin.cli.BasicCommand;
import urchin.cli.Command;

import java.nio.file.Path;

@Component
public class UnmountFolderCommand extends BasicCommand {

    private static final String FOLDER = "%folder%";
    private static final String UNMOUNT_FOLDER = "unmount-folder";

    private final Command command;

    @Autowired
    public UnmountFolderCommand(Runtime runtime, Command command) {
        super(runtime);
        this.command = command;
    }

    public void execute(Path path) {
        log.info("Unmounting folder {}", path.toAbsolutePath());
        executeCommand(command.getFolderCommand(UNMOUNT_FOLDER)
                .replace(FOLDER, path.toAbsolutePath().toString())
        );
    }

}
