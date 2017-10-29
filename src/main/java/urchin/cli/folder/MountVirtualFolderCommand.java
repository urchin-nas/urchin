package urchin.cli.folder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urchin.cli.Command;
import urchin.cli.common.BasicCommand;

import java.nio.file.Path;
import java.util.List;

import static org.springframework.util.StringUtils.arrayToDelimitedString;

@Component
public class MountVirtualFolderCommand extends BasicCommand {

    private static final String FOLDER_LIST = "%folderList%";
    private static final String VIRTUAL_FOLDER = "%virtualFolder%";

    private final Command command;

    @Autowired
    public MountVirtualFolderCommand(Runtime runtime, Command command) {
        super(runtime);
        this.command = command;
    }

    public void execute(List<Path> folders, Path virtualFolder) {
        LOG.debug("Mounting virtual folder {} for {} folders", virtualFolder.toAbsolutePath(), folders.size());
        executeCommand(command.getFolderCommand("mount-virtual-folder")
                .replace(FOLDER_LIST, arrayToDelimitedString(folders.toArray(), ","))
                .replace(VIRTUAL_FOLDER, virtualFolder.toAbsolutePath().toString())
        );
    }
}
