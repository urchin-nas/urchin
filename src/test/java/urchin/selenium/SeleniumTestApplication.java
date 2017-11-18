package urchin.selenium;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import urchin.selenium.configuration.SeleniumPortConfiguration;
import urchin.selenium.configuration.SeleniumWebDriverConfiguration;

@RunWith(SpringRunner.class)
@Import({JarExecutor.class, SeleniumPortConfiguration.class, SeleniumWebDriverConfiguration.class})
public abstract class SeleniumTestApplication {

    @Autowired
    @Qualifier("seleniumWebDriver")
    WebDriver webDriver;

    @Autowired
    @Qualifier("seleniumPort")
    private Integer port;

    @Rule
    @Autowired
    public JarExecutor jarExecutor;

    static String url;

    @Before
    public void setupSeleniumTestApplication() {
        url = "http://localhost:" + port;
    }
}
