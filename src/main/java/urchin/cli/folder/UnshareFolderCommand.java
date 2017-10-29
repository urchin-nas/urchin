package urchin.cli.folder;

import org.springframework.stereotype.Component;
import urchin.cli.Command;
import urchin.cli.common.BasicCommand;

import java.nio.file.Path;

@Component
public class UnshareFolderCommand extends BasicCommand {

    private static final String FOLDER = "%folder%";

    private final Command command;

    public UnshareFolderCommand(Runtime runtime, Command command) {
        super(runtime);
        this.command = command;
    }

    public void execute(Path folder) {
        LOG.info("Unsharing folder {}", folder);
        executeCommand(command.getFolderCommand("unshare-folder")
                .replace(FOLDER, folder.getFileName().toString())
        );
    }
}
