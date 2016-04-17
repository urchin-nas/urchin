package urchin.testutil;

import org.junit.rules.TemporaryFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import urchin.domain.shell.UnmountFolderShellCommand;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static urchin.util.EncryptedFolderUtil.SLASH_HIDDEN_FOLDER;

public class TemporaryFolderUmount extends TemporaryFolder {

    private static final Logger LOG = LoggerFactory.getLogger(TemporaryFolderUmount.class);

    private UnmountFolderShellCommand unmountFolderShellCommand = new UnmountFolderShellCommand(Runtime.getRuntime());

    @Override
    protected void after() {
        unmountFolders();
        super.after();
    }

    private void unmountFolders() {
        Path rootPath = Paths.get(getRoot().getAbsolutePath());
        LOG.info("Unmounting folders in {}", rootPath.toAbsolutePath());
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(rootPath)) {
            for (Path path : directoryStream) {
                if (Files.isDirectory(path) && !path.toAbsolutePath().toString().contains(SLASH_HIDDEN_FOLDER)) {
                    try {
                        unmountFolderShellCommand.execute(path);
                    } catch (Exception e) {
                        LOG.error("Error during umount", e);
                    }
                }
            }
        } catch (IOException ignore) {
        }
    }
}
