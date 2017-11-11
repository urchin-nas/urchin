package urchin.selenium;

import org.junit.Test;
import org.openqa.selenium.By;

public class HomeIT extends SeleniumTestApplication {

    @Test
    public void homeIsDisplayedAsFirstPageWhenLoggedIn() {
        webDriver.get(url);

        webDriver.findElement(By.className("home"));
    }
}
