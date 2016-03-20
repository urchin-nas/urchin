package urchin.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Arrays;

public class MountEncryptedFolderShellCommand implements ShellCommand {

    private static final Logger LOG = LoggerFactory.getLogger(MountEncryptedFolderShellCommand.class);
    public static final String ENCRYPTED_FOLDER_PATH = "%encryptedFolderPath%";
    public static final String FOLDER_PATH = "%folderPath%";
    public static final String PASSPHRASE = "%passphrase%";

    private static final String[] COMMAND = new String[]{"sudo", "mount", "-t", "ecryptfs", ENCRYPTED_FOLDER_PATH, FOLDER_PATH, "-o", "key=passphrase:passphrase_passwd=" + PASSPHRASE + ",ecryptfs_cipher=aes,ecryptfs_key_bytes=16,ecryptfs_passthrough=n,no_sig_cache=n,ecryptfs_enable_filename_crypto=y"};

    private final Runtime runTime;
    private final String folderPath;
    private final String encryptedFolderPath;
    private final String passphrase;

    public MountEncryptedFolderShellCommand(Runtime runTime, String folderPath, String encryptedFolderPath, String passphrase) {
        this.runTime = runTime;
        this.folderPath = folderPath;
        this.encryptedFolderPath = encryptedFolderPath;
        this.passphrase = passphrase;
    }

    public String execute() {
        LOG.debug("Mounting encrypted folder {} to {}", encryptedFolderPath, folderPath);
        String[] command = setupCommand();
        try {
            StringBuilder result = new StringBuilder();
            Process process = runTime.exec(command);
            OutputStream outputStream = process.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            bufferedWriter.newLine();
            bufferedWriter.flush();
            process.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
            System.out.println("process.exitValue = " + process.exitValue());
            System.out.println("result = " + result);

            return result.toString();
        } catch (Exception e) {
            LOG.error("Failed to execute command");
            throw new RuntimeException(e);
        }
    }

    private String[] setupCommand() {
        String[] command = Arrays.copyOf(COMMAND, COMMAND.length);
        command[4] = encryptedFolderPath;
        command[5] = folderPath;
        command[7] = command[7].replace(PASSPHRASE, passphrase);
        return command;
    }
}
