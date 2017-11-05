package urchin.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import urchin.cli.folder.*;
import urchin.model.folder.EncryptedFolder;
import urchin.model.folder.Folder;
import urchin.model.folder.Passphrase;
import urchin.model.folder.VirtualFolder;

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

    public void mountEncryptedFolder(Folder folder, EncryptedFolder encryptedFolder, Passphrase passphrase) {
        mountEncryptedFolderCommand.execute(folder, encryptedFolder, passphrase);
    }

    public void mountVirtualFolder(List<Folder> folders, VirtualFolder virtualFolder) {
        mountVirtualFolderCommand.execute(folders, virtualFolder);
    }

    public void restartSamba() {
        restartSambaCommand.execute();
    }

    public void shareFolder(Folder folder) {
        shareFolderCommand.execute(folder);
    }

    public void unmountFolder(Folder folder) {
        unmountFolderCommand.execute(folder.getPath());
    }

    public void unmountFolder(EncryptedFolder encryptedFolder) {
        unmountFolderCommand.execute(encryptedFolder.getPath());
    }

    public void unmountFolder(VirtualFolder virtualFolder) {
        unmountFolderCommand.execute(virtualFolder.getPath());
    }

    public void unshareFolder(Folder folder) {
        unshareFolderCommand.execute(folder);
    }
}
