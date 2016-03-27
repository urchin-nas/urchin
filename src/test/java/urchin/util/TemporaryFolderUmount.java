package urchin.util;

import org.junit.rules.TemporaryFolder;
import urchin.shell.UmountFolderShellCommand;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static urchin.util.EncryptedFolderUtil.SLASH_HIDDEN_FOLDER;

public class TemporaryFolderUmount extends TemporaryFolder {

    private UmountFolderShellCommand umountFolderShellCommand = new UmountFolderShellCommand(Runtime.getRuntime());

    @Override
    protected void after() {
        umountFolders();
        super.after();
    }

    private void umountFolders() {
        Path rootPath = Paths.get(getRoot().getAbsolutePath());
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(rootPath)) {
            for (Path path : directoryStream) {
                if (Files.isDirectory(path) && !path.toAbsolutePath().toString().contains(SLASH_HIDDEN_FOLDER)) {
                    try {
                        umountFolderShellCommand.execute(path);
                    } catch (Exception ignore) {
                    }
                }
            }
        } catch (IOException ignore) {
        }
    }
}
