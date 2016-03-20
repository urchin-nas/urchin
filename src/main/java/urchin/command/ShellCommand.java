package urchin.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Arrays;

public class ShellCommand {

    private static final Logger log = LoggerFactory.getLogger(ShellCommand.class);

    private final Runtime runTime;

    public ShellCommand(Runtime runTime) {
        this.runTime = runTime;
    }

    public void execute(urchin.domain.ShellCommand shellCommand) {
        /*
        executeCommand(new String[]{"mkdir", "/media/andreas/New Volume/.secret"});
        executeCommand(new String[]{"mkdir", "/media/andreas/New Volume/secret"});
        */
        executeCommand(new String[]{"sudo", "mount", "-t", "ecryptfs", "/media/andreas/New Volume/.secret", "/media/andreas/New Volume/secret", "-o", "key=passphrase:passphrase_passwd=lolboll,ecryptfs_cipher=aes,ecryptfs_key_bytes=16,ecryptfs_passthrough=n,no_sig_cache=n,ecryptfs_enable_filename_crypto=y"});
    }

    private String executeCommand(String[] command) {
        log.debug("Executing command {}", Arrays.toString(command));
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
            log.error("Failed to execute command: {}", Arrays.toString(command));
            throw new RuntimeException(e);
        }


    }
}
