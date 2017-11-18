package urchin.selenium;

import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Pattern;

import static urchin.selenium.configuration.SeleniumPortConfiguration.PORT;

@TestComponent
public class JarExecutor extends ExternalResource {

    private static final String JAR_PATTERN = "urchin-.*.jar";
    private static final String HEALTH_ENDPOINT = "http://localhost:" + PORT + "/health";
    private static final String PROFILE = "production";

    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private final boolean executeJar;

    private Process jarProcess;

    @Autowired
    public JarExecutor(Environment environment) {
        executeJar = Arrays.asList(environment.getActiveProfiles()).contains(PROFILE);
    }

    @Override
    protected void before() throws Throwable {
        if (executeJar) {
            Path jar = findJar();
            log.info("Starting jar " + jar.toAbsolutePath());
            jarProcess = Runtime.getRuntime().exec(getJavaExecutable() + " -jar " + jar.toAbsolutePath().toString());
            startProcessOutputReader();
            waitForJarToStart();
        } else {
            log.info("profile '{}' not enabled. Expecting webpack-dev-server and backend to have been started manually", PROFILE);
        }
    }

    @Override
    protected void after() {
        if (jarProcess != null && jarProcess.isAlive()) {
            log.info("Shutting down jar " + JAR_PATTERN);
            jarProcess.destroyForcibly();
        }
    }

    private String getJavaExecutable() {
        String executable = System.getenv("JAVA_HOME");
        if (executable == null || executable.isEmpty()) {
            executable = "java";
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
        Path targetDirectory = Paths.get(String.format("%s/target/", System.getProperty("user.dir")));
        log.info("Searching for jar {} in {}", JAR_PATTERN, targetDirectory.toAbsolutePath());

        Optional<Path> jar = Files.walk(targetDirectory)
                .filter(path -> Pattern.matches(JAR_PATTERN, path.getFileName().toString()))
                .findFirst();

        if (!jar.isPresent()) {
            String message = String.format("Jar not found! Expected to find jar of pattern %s in %s. Rebuild project to try again", JAR_PATTERN, targetDirectory.toAbsolutePath());
            throw new IllegalStateException(message);
        }

        log.info("Found jar {}", jar.get().toAbsolutePath());
        return jar.get();
    }
}
