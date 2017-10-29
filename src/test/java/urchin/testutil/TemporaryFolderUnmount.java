package urchin.testutil;

import org.junit.rules.TemporaryFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import urchin.cli.Command;
import urchin.cli.folder.UnmountFolderCommand;
import urchin.configuration.CommandConfiguration;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static urchin.util.EncryptedFolderUtil.HIDDEN_FOLDER_DELIMITER;

public class TemporaryFolderUnmount extends TemporaryFolder {

    private static final Logger LOG = LoggerFactory.getLogger(TemporaryFolderUnmount.class);

    private UnmountFolderCommand unmountFolderCommand;

    public TemporaryFolderUnmount() {
        CommandConfiguration commandConfiguration = new CommandConfiguration();
        try {
            unmountFolderCommand = new UnmountFolderCommand(Runtime.getRuntime(), new Command(commandConfiguration.yamlPropertySourceLoader()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
