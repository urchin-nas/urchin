package urchin.selenium.testutil;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

public class SeleniumRunner extends SpringJUnit4ClassRunner {

    static {
        java.util.logging.Logger.getLogger("org.apache.http.impl.client").setLevel(java.util.logging.Level.WARNING);
    }


    public SeleniumRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    public void run(RunNotifier notifier) {
        JarExecutor jarExecutor = new JarExecutor();
        notifier.addListener(new SeleniumExecutionListener(jarExecutor));
        notifier.fireTestRunStarted(getDescription());
        super.run(notifier);
    }
}
