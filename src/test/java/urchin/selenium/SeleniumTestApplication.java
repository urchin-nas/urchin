package urchin.selenium;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import urchin.selenium.configuration.WebDriverConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(WebDriverConfiguration.class)
public abstract class SeleniumTestApplication {

    @LocalServerPort
    private int port;

    @Autowired
    protected WebDriver webDriver;


    static String url;

    @Before
    public void setupSeleniumTestApplication() {
        url = "http://localhost:" + port;
    }
}
