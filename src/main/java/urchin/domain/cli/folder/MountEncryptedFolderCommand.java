package urchin.domain.cli.folder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urchin.domain.cli.common.CommandException;
import urchin.domain.model.EncryptedFolder;
import urchin.domain.model.Passphrase;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.nio.file.Path;

import static java.util.Arrays.copyOf;

@Component
public class MountEncryptedFolderCommand {

    private static final Logger LOG = LoggerFactory.getLogger(MountEncryptedFolderCommand.class);
    private static final String ENCRYPTED_FOLDER_PATH = "%encryptedFolderPath%";
    private static final String FOLDER_PATH = "%folderPath%";
    private static final String PASSPHRASE = "%passphrase%";

    private static final String[] COMMAND = new String[]{
            "sudo",
            "mount",
            "-t",
            "ecryptfs",
            ENCRYPTED_FOLDER_PATH, FOLDER_PATH,
            "-o",
            "key=passphrase:passphrase_passwd=" + PASSPHRASE + ",ecryptfs_cipher=aes,ecryptfs_key_bytes=16,ecryptfs_passthrough=n,no_sig_cache=n,ecryptfs_enable_filename_crypto=y"
    };

    private final Runtime runtime;

    @Autowired
    public MountEncryptedFolderCommand(Runtime runtime) {
        this.runtime = runtime;
    }

    public void execute(Path folder, EncryptedFolder encryptedFolder, Passphrase passphrase) {
        LOG.info("Setting up encrypted folder {} and mounting it to {}", encryptedFolder.getPath(), folder);
        String[] command = setupCommand(folder, encryptedFolder, passphrase);
        try {
            Process process = runtime.exec(command);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
            bufferedWriter.newLine();
            bufferedWriter.flush();
            process.waitFor();
            if (process.exitValue() != 0) {
                throw new CommandException(this.getClass().getName(), process.exitValue());
            }
        } catch (Exception e) {
            LOG.error("Failed to execute command");
            throw new CommandException(this.getClass().getName(), e);
        }
    }

    private String[] setupCommand(Path folder, EncryptedFolder encryptedFolder, Passphrase passphrase) {
        String[] command = copyOf(COMMAND, COMMAND.length);
        command[4] = encryptedFolder.getPath().toAbsolutePath().toString();
        command[5] = folder.toAbsolutePath().toString();
        command[7] = command[7].replace(PASSPHRASE, passphrase.getPassphrase());
        return command;
    }
}