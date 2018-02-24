package urchin.selenium.testutil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.regex.Pattern;

import static java.lang.String.format;
import static urchin.selenium.testutil.ProfileEvaluator.PROFILE;
import static urchin.selenium.testutil.ProfileEvaluator.executeJar;
import static urchin.selenium.testutil.SeleniumUrl.PORT;

public enum JarExecutor {

    INSTANCE;

    private static final String JAR_PATTERN = "urchin-.*.jar";
    private static final String HEALTH_ENDPOINT = "http://localhost:" + PORT + "/health";

    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private final boolean executeJar;

    private static Process jarProcess;

    JarExecutor() {
        executeJar = executeJar();
    }

    void start() throws Exception {
        if (executeJar) {
            initJar();
        } else {
            log.info("profile '{}' not enabled. Expecting webpack-dev-server and backend to have been started manually", PROFILE);
        }
    }

    void stop() {
        if (isStarted()) {
            log.info("Shutting down jar " + JAR_PATTERN);
            jarProcess.destroyForcibly();
        }
    }

    private void initJar() throws IOException, InterruptedException {
        if (!isStarted()) {
            Path jar = findJar();
            log.info("Starting jar " + jar.toAbsolutePath());
            String temporaryFolderPath = System.getProperty("java.io.tmpdir");
            String execute = format("%s -Dspring.datasource.url=jdbc:h2:%s/urchintestdb -jar %s", getJavaExecutable(), temporaryFolderPath, jar.toAbsolutePath().toString());
            log.info(execute);
            jarProcess = Runtime.getRuntime().exec(execute);
            startProcessOutputReader();
            waitForJarToStart();
        }
    }

    private boolean isStarted() {
        return jarProcess != null && jarProcess.isAlive();
    }

    private String getJavaExecutable() {
        String executable = System.getenv("JAVA_HOME");
        if (executable == null || executable.isEmpty()) {
            executable = "java";
        } else {
            executable = executable + "/bin/java";
        }
        log.info("java executable {}", executable);
        return executable;
    }

    private void startProcessOutputReader() {
        Runnable task = () -> {
            BufferedReader reader = new BufferedReader(new InputStreamReader(jarProcess.getInputStream()));
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    log.info(line);
                }
            } catch (IOException ignore) {
            }
        };
        log.info("Starting process output reader in new thread");
        Thread thread = new Thread(task);
        thread.start();
    }

    private void waitForJarToStart() throws InterruptedException {
        RestTemplate restTemplate = new RestTemplate();
        int i = 0;
        while (i < 60) {
            i++;
            log.info("Checking if jar has started. Times " + i);
            try {
                if (restTemplate.getForEntity(HEALTH_ENDPOINT, String.class).getStatusCode() == HttpStatus.OK) {
                    log.info("Jar has started, proceed with tests");
                    return;
                }
            } catch (Exception ignore) {
            }
            Thread.sleep(1000);
        }
        jarProcess.destroyForcibly();
        throw new RuntimeException("failed to start jar");
    }

    private Path findJar() throws IOException {
        Path targetDirectory = Paths.get(format("%s/target/", System.getProperty("user.dir")));
        log.info("Searching for jar {} in {}", JAR_PATTERN, targetDirectory.toAbsolutePath());

        Optional<Path> jar = Files.walk(targetDirectory)
                .filter(path -> Pattern.matches(JAR_PATTERN, path.getFileName().toString()))
                .findFirst();

        if (!jar.isPresent()) {
            String message = format("Jar not found! Expected to find jar of pattern %s in %s. Rebuild project and try again", JAR_PATTERN, targetDirectory.toAbsolutePath());
            throw new IllegalStateException(message);
        }

        log.info("Found jar {}", jar.get().toAbsolutePath());
        return jar.get();
    }
}
