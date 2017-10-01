package urchin.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import urchin.cli.folder.*;
import urchin.model.folder.EncryptedFolder;
import urchin.model.folder.Passphrase;

import java.nio.file.Path;
import java.util.List;

@Repository
public class FolderCli {

    private final MountEncryptedFolderCommand mountEncryptedFolderCommand;
    private final MountVirtualFolderCommand mountVirtualFolderCommand;
    private final RestartSambaCommand restartSambaCommand;
    private final ShareFolderCommand shareFolderCommand;
    private final UnmountFolderCommand unmountFolderCommand;
    private final UnshareFolderCommand unshareFolderCommand;

    @Autowired
    public FolderCli(
            MountEncryptedFolderCommand mountEncryptedFolderCommand,
            MountVirtualFolderCommand mountVirtualFolderCommand,
            RestartSambaCommand restartSambaCommand,
            ShareFolderCommand shareFolderCommand,
            UnmountFolderCommand unmountFolderCommand,
            UnshareFolderCommand unshareFolderCommand
    ) {
        this.mountEncryptedFolderCommand = mountEncryptedFolderCommand;
        this.mountVirtualFolderCommand = mountVirtualFolderCommand;
        this.restartSambaCommand = restartSambaCommand;
        this.shareFolderCommand = shareFolderCommand;
        this.unmountFolderCommand = unmountFolderCommand;
        this.unshareFolderCommand = unshareFolderCommand;
    }

    public void mountEncryptedFolder(Path folder, EncryptedFolder encryptedFolder, Passphrase passphrase) {
        mountEncryptedFolderCommand.execute(folder, encryptedFolder, passphrase);
    }

    public void mountVirtualFolder(List<Path> folders, Path virtualFolder) {
        mountVirtualFolderCommand.execute(folders, virtualFolder);
    }

    public void restartSamba() {
        restartSambaCommand.execute();
    }

    public void shareFolder(Path folder) {
        shareFolderCommand.execute(folder);
    }

    public void unmountFolder(Path folder) {
        unmountFolderCommand.execute(folder);
    }

    public void unshareFolder(Path folder) {
        unshareFolderCommand.execute(folder);
    }
}
