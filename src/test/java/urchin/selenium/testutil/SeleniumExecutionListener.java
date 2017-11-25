package urchin.selenium.testutil;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;

public class SeleniumExecutionListener extends RunListener {

    private final JarExecutor jarExecutor;

    public SeleniumExecutionListener(JarExecutor jarExecutor) {
        super();
        this.jarExecutor = jarExecutor;
    }

    @Override
    public void testRunStarted(Description description) throws Exception {
        jarExecutor.start();
        super.testRunStarted(description);
    }

    @Override
    public void testRunFinished(Result result) throws Exception {
        jarExecutor.stop();
        SeleniumDriver.getDriver().quit();
        super.testRunFinished(result);
    }
}
