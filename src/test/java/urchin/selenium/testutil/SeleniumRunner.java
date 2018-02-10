package urchin.selenium.testutil;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

public class SeleniumRunner extends SpringJUnit4ClassRunner {

    public SeleniumRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    public void run(RunNotifier notifier) {
        notifier.addListener(new SeleniumExecutionListener());
        notifier.fireTestRunStarted(getDescription());
        super.run(notifier);
    }
}
