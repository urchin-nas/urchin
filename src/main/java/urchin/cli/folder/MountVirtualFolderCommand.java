package urchin.cli.folder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urchin.cli.BasicCommand;
import urchin.cli.Command;
import urchin.model.folder.Folder;
import urchin.model.folder.FolderWrapper;
import urchin.model.folder.VirtualFolder;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.arrayToDelimitedString;

@Component
public class MountVirtualFolderCommand extends BasicCommand {

    private static final String FOLDER_LIST = "%folderList%";
    private static final String VIRTUAL_FOLDER = "%virtualFolder%";
    private static final String MOUNT_VIRTUAL_FOLDER = "mount-virtual-folder";

    private final Command command;

    @Autowired
    public MountVirtualFolderCommand(Runtime runtime, Command command) {
        super(runtime);
        this.command = command;
    }

    public void execute(List<Folder> folders, VirtualFolder virtualFolder) {
        log.info("Mounting virtual folder {} for {} folders", virtualFolder.toAbsolutePath(), folders.size());
        List<String> folderPaths = folders.stream()
                .map(FolderWrapper::toAbsolutePath)
                .collect(Collectors.toList());
        executeCommand(command.getFolderCommand(MOUNT_VIRTUAL_FOLDER)
                .replace(FOLDER_LIST, arrayToDelimitedString(folderPaths.toArray(), ","))
                .replace(VIRTUAL_FOLDER, virtualFolder.toAbsolutePath())
        );
    }
}
