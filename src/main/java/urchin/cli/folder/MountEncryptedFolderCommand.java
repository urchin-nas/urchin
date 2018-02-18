package urchin.cli.folder;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urchin.cli.Command;
import urchin.exception.CommandException;
import urchin.model.folder.EncryptedFolder;
import urchin.model.folder.Folder;
import urchin.model.folder.Passphrase;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

import static java.nio.charset.Charset.defaultCharset;

@Component
public class MountEncryptedFolderCommand {

    private static final String ENCRYPTED_FOLDER = "%encryptedFolder%";
    private static final String FOLDER = "%folder%";
    private static final String PASSPHRASE = "%passphrase%";
    private static final String MOUNT_ENCRYPTED_FOLDER = "mount-encrypted-folder";

    private final Logger log = LoggerFactory.getLogger(MountEncryptedFolderCommand.class);
    private final Runtime runtime;
    private final Command command;

    @Autowired
    public MountEncryptedFolderCommand(Runtime runtime, Command command) {
        this.runtime = runtime;
        this.command = command;
    }

    public void execute(Folder folder, EncryptedFolder encryptedFolder, Passphrase passphrase) {
        String folderAbsolutePath = folder.toAbsolutePath();
        String encryptedFolderAbsolutePath = encryptedFolder.toAbsolutePath();
        log.info("Setting up encrypted folder {} and mounting it to {}", encryptedFolderAbsolutePath, folderAbsolutePath);

        String[] mntCommand = command.getFolderCommand(MOUNT_ENCRYPTED_FOLDER)
                .replace(ENCRYPTED_FOLDER, encryptedFolderAbsolutePath)
                .replace(FOLDER, folderAbsolutePath)
                .replace(PASSPHRASE, passphrase.getValue())
                .split(" ");

        try {
            Process process = runtime.exec(mntCommand);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
            bufferedWriter.newLine();
            bufferedWriter.flush();
            process.waitFor();
            if (process.exitValue() != 0) {
                String error = IOUtils.toString(process.getErrorStream(), defaultCharset());
                log.error("Process failed with error: {}", error);
                throw new CommandException(this.getClass(), process.exitValue());
            }
        } catch (CommandException e) {
            throw e;
        } catch (Exception e) {
            log.error("Failed to execute command");
            throw new CommandException(this.getClass(), e);
        }
    }
}
