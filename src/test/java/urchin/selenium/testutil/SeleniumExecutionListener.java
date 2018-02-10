package urchin.selenium.testutil;

import org.apache.commons.io.FileUtils;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class SeleniumExecutionListener extends RunListener {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    public SeleniumExecutionListener() {
        super();
    }

    @Override
    public void testRunStarted(Description description) throws Exception {
        JarExecutor.INSTANCE.start();
        super.testRunStarted(description);
    }

    @Override
    public void testRunFinished(Result result) throws Exception {
        JarExecutor.INSTANCE.stop();
        SeleniumDriver.getDriver().quit();
        super.testRunFinished(result);
    }

    @Override
    public void testFailure(Failure failure) throws Exception {
        captureScreenshot(failure);
        super.testFailure(failure);
    }

    private void captureScreenshot(Failure failure) throws IOException {
        String filename = String.format("%s - %s.png", failure.getDescription().getClassName(), failure.getDescription().getMethodName());
        String fullPath = String.format("%s/target/screenshots/%s", System.getProperty("user.dir"), filename);
        log.info("Taking screenshot " + fullPath);

        File scrFile = ((TakesScreenshot) SeleniumDriver.getDriver()).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File(fullPath));
    }
}
