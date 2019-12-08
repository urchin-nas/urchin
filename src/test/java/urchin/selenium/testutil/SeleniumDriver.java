package urchin.selenium.testutil;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static urchin.selenium.testutil.ProfileEvaluator.executeJar;


public enum SeleniumDriver {

    SELENIUM_DRIVER;

    private final Logger log = LoggerFactory.getLogger(SeleniumDriver.class.getName());

    private WebDriver driver;

    SeleniumDriver() {
        if (executeJar()) {
            driver = createHeadlessDriver();
        } else {
            driver = createDriver();
        }
    }

    public WebDriver getDriver() {
        return driver;
    }

    private WebDriver createDriver() {
        log.info("Configuring webDriver to execute in normal mode");
        WebDriverManager webDriverManager = ChromeDriverManager.chromedriver();
        webDriverManager.setup();

        ChromeOptions options = new ChromeOptions();

        return new ChromeDriver(options);
    }

    private WebDriver createHeadlessDriver() {
        log.info("Configuring webDriver to execute in headless mode");
        WebDriverManager webDriverManager = ChromeDriverManager.chromedriver();
        webDriverManager.setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");

        return new ChromeDriver(options);
    }
}
