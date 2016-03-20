package urchin.service;

import org.junit.Test;
import urchin.shell.MountEncryptedFolderShellCommand;
import urchin.shell.MountVirtualFolderShellCommand;

import java.util.Arrays;

public class FolderServiceTest {

    @Test
    public void rofl() {
        MountEncryptedFolderShellCommand mountEncryptedFolderShellCommand = new MountEncryptedFolderShellCommand(Runtime.getRuntime());
        MountVirtualFolderShellCommand mountVirtualFolderShellCommand = new MountVirtualFolderShellCommand(Runtime.getRuntime());
        FolderService folderService = new FolderService(mountEncryptedFolderShellCommand, mountVirtualFolderShellCommand);

        String folderPath = "/media/andreas/New Volume/epic";
        String folderPath1 = "/media/andreas/New Volume2/epic1";
        String virtualFolder = "/media/andreas/New Volume/virtual";
        folderService.setupEncryptedFolder(folderPath, "lolboll");
        folderService.setupEncryptedFolder(folderPath1, "lolboll1");
        folderService.setupVirtualFolder(Arrays.asList(folderPath, folderPath1), virtualFolder);

    }

}