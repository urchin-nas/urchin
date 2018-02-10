package urchin.selenium.testutil;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static urchin.selenium.testutil.ProfileEvaluator.executeJar;


public class SeleniumDriver {

    private static final Logger log = LoggerFactory.getLogger(SeleniumDriver.class.getName());
    private static WebDriver driver;

    public static synchronized WebDriver getDriver() {
        if (driver == null) {
            if (executeJar()) {
                driver = createHeadlessDriver();
            } else {
                driver = createDriver();
            }
        }

        return driver;
    }

    private static WebDriver createDriver() {
        log.info("Configuring webDriver to execute in normal mode");
        WebDriverManager webDriverManager = ChromeDriverManager.getInstance();
        webDriverManager.setup();

        ChromeOptions options = new ChromeOptions();

        return new ChromeDriver(options);
    }

    private static WebDriver createHeadlessDriver() {
        log.info("Configuring webDriver to execute in headless mode");
        WebDriverManager webDriverManager = ChromeDriverManager.getInstance();
        webDriverManager.setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");

        return new ChromeDriver(options);
    }
}
