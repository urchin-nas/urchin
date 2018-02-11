package urchin.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import urchin.cli.folder.*;
import urchin.model.folder.*;

import java.util.List;

@Repository
public class FolderCli {

    private final MountEncryptedFolderCommand mountEncryptedFolderCommand;
    private final MountVirtualFolderCommand mountVirtualFolderCommand;
    private final RestartSambaCommand restartSambaCommand;
    private final ShareFolderCommand shareFolderCommand;
    private final UnmountFolderCommand unmountFolderCommand;
    private final UnshareFolderCommand unshareFolderCommand;
    private final CreateFolderCommand createFolderCommand;
    private final RemoveFolderCommand removeFolderCommand;
    private final SetFolderImmutableCommand setFolderImmutableCommand;
    private final SetFolderMutableCommand setFolderMutableCommand;

    @Autowired
    public FolderCli(
            MountEncryptedFolderCommand mountEncryptedFolderCommand,
            MountVirtualFolderCommand mountVirtualFolderCommand,
            RestartSambaCommand restartSambaCommand,
            ShareFolderCommand shareFolderCommand,
            UnmountFolderCommand unmountFolderCommand,
            UnshareFolderCommand unshareFolderCommand,
            CreateFolderCommand createFolderCommand,
            RemoveFolderCommand removeFolderCommand,
            SetFolderImmutableCommand setFolderImmutableCommand,
            SetFolderMutableCommand setFolderMutableCommand) {
        this.mountEncryptedFolderCommand = mountEncryptedFolderCommand;
        this.mountVirtualFolderCommand = mountVirtualFolderCommand;
        this.restartSambaCommand = restartSambaCommand;
        this.shareFolderCommand = shareFolderCommand;
        this.unmountFolderCommand = unmountFolderCommand;
        this.unshareFolderCommand = unshareFolderCommand;
        this.createFolderCommand = createFolderCommand;
        this.removeFolderCommand = removeFolderCommand;
        this.setFolderImmutableCommand = setFolderImmutableCommand;
        this.setFolderMutableCommand = setFolderMutableCommand;
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

    public void unmountFolder(FolderWrapper folder) {
        unmountFolderCommand.execute(folder.getPath());
    }

    public void unshareFolder(Folder folder) {
        unshareFolderCommand.execute(folder);
    }

    public void createFolder(FolderWrapper folder) {
        createFolderCommand.execute(folder);
    }

    public void removeFolder(FolderWrapper folder) {
        removeFolderCommand.execute(folder);
    }

    public void setFolderImmutable(Folder folder) {
        setFolderImmutableCommand.execute(folder);
    }

    public void setFolderMutable(Folder folder) {
        setFolderMutableCommand.execute(folder);
    }
}
