package urchin.service;

import org.junit.Test;
import urchin.shell.MountEncryptedFolderShellCommand;

public class FolderServiceTest {

    @Test
    public void rofl() {
        MountEncryptedFolderShellCommand mountEncryptedFolderShellCommand = new MountEncryptedFolderShellCommand(Runtime.getRuntime());
        FolderService folderService = new FolderService(mountEncryptedFolderShellCommand);

        folderService.setupEncryptedFolder("/media/andreas/New Volume1/epic", "lolboll");
    }

}