package urchin.shell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class MountEncryptedFolderShellCommand {

    private static final Logger LOG = LoggerFactory.getLogger(MountEncryptedFolderShellCommand.class);
    public static final String ENCRYPTED_FOLDER_PATH = "%encryptedFolderPath%";
    public static final String FOLDER_PATH = "%folderPath%";
    public static final String PASSPHRASE = "%passphrase%";

    private static final String[] COMMAND = new String[]{"sudo", "mount", "-t", "ecryptfs", ENCRYPTED_FOLDER_PATH, FOLDER_PATH, "-o", "key=passphrase:passphrase_passwd=" + PASSPHRASE + ",ecryptfs_cipher=aes,ecryptfs_key_bytes=16,ecryptfs_passthrough=n,no_sig_cache=n,ecryptfs_enable_filename_crypto=y"};

    private final Runtime runtime;

    public MountEncryptedFolderShellCommand(Runtime runtime) {
        this.runtime = runtime;
    }

    public void execute(String folderPath, String encryptedFolderPath, String passphrase) {
        LOG.debug("Mounting encrypted folder {} to {}", encryptedFolderPath, folderPath);
        String[] command = setupCommand(folderPath, encryptedFolderPath, passphrase);
        try {
            Process process = runtime.exec(command);
            OutputStream outputStream = process.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            bufferedWriter.newLine();
            bufferedWriter.flush();
            process.waitFor();
            if (process.exitValue() != 0) {
                throw new ShellCommandException("Process returned code: " + process.exitValue());
            }
        } catch (Exception e) {
            LOG.error("Failed to execute command");
            throw new ShellCommandException(e);
        }
    }

    private String[] setupCommand(String folderPath, String encryptedFolderPath, String passphrase) {
        String[] command = Arrays.copyOf(COMMAND, COMMAND.length);
        command[4] = encryptedFolderPath;
        command[5] = folderPath;
        command[7] = command[7].replace(PASSPHRASE, passphrase);
        return command;
    }
}
