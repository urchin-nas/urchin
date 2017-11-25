package urchin.selenium.testutil;

import org.junit.Rule;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import urchin.selenium.configuration.SeleniumUrlConfiguration;
import urchin.selenium.configuration.SeleniumWebDriverConfiguration;
import urchin.selenium.view.PageView;

@RunWith(SpringRunner.class)
@Import({JarExecutor.class, SeleniumUrlConfiguration.class, SeleniumWebDriverConfiguration.class})
@ComponentScan(basePackageClasses = PageView.class)
public abstract class SeleniumTestApplication {

    @Autowired
    @Qualifier("seleniumWebDriver")
    protected WebDriver driver;

    @Autowired
    @Qualifier("seleniumUrl")
    protected String url;

    @Rule
    @Autowired
    public JarExecutor jarExecutor;

}
