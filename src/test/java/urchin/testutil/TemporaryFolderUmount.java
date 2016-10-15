package urchin.testutil;

import org.junit.rules.TemporaryFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import urchin.domain.cli.UnmountFolderCommand;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static urchin.domain.util.EncryptedFolderUtil.HIDDEN_FOLDER_DELIMITER;

public class TemporaryFolderUmount extends TemporaryFolder {

    private static final Logger LOG = LoggerFactory.getLogger(TemporaryFolderUmount.class);

    private UnmountFolderCommand unmountFolderCommand = new UnmountFolderCommand(Runtime.getRuntime());

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
                if (Files.isDirectory(path) && !path.toAbsolutePath().toString().contains(HIDDEN_FOLDER_DELIMITER)) {
                    try {
                        unmountFolderCommand.execute(path);
                    } catch (Exception e) {
                        LOG.debug("Error during unmount", e);
                    }
                }
            }
        } catch (IOException ignore) {
        }
    }
}
