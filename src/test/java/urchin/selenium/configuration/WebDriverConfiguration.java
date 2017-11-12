package urchin.selenium.configuration;

import io.github.bonigarcia.wdm.BrowserManager;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@TestConfiguration
public class WebDriverConfiguration {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Bean
    @Profile("production")
    public WebDriver webDriver() {
        log.info("Configuring webDriver to execute in headless mode");
        BrowserManager browserManager = ChromeDriverManager.getInstance();
        browserManager.setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");

        return new ChromeDriver(options);
    }

    @Bean
    @Profile("!production")
    public WebDriver devWebDriver() {
        log.info("Configuring webDriver to execute in normal mode");
        BrowserManager browserManager = ChromeDriverManager.getInstance();
        browserManager.setup();

        ChromeOptions options = new ChromeOptions();

        return new ChromeDriver(options);
    }
}
