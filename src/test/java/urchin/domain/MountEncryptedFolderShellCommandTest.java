package urchin.domain;

import org.junit.Before;
import org.junit.Test;
import urchin.command.*;

import static org.junit.Assert.*;

public class MountEncryptedFolderShellCommandTest {

    @Test
    public void rofl() {

        MountEncryptedFolderShellCommand mountEncryptedFolderShellCommand = new MountEncryptedFolderShellCommand(Runtime.getRuntime(), "/media/andreas/New Volume/secret", "/media/andreas/New Volume/.secret", "lolboll");
        mountEncryptedFolderShellCommand.execute();
    }

}