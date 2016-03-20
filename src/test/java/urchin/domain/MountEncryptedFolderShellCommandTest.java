package urchin.domain;

import org.junit.Test;

public class MountEncryptedFolderShellCommandTest {

    @Test
    public void rofl() {

        MountEncryptedFolderShellCommand mountEncryptedFolderShellCommand = new MountEncryptedFolderShellCommand(Runtime.getRuntime(), "/media/andreas/New Volume/secret", "/media/andreas/New Volume/.secret", "lolboll");
        mountEncryptedFolderShellCommand.execute();
    }

}