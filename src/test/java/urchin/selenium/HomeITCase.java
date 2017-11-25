package urchin.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import urchin.selenium.testutil.SeleniumTestApplication;

public class HomeITCase extends SeleniumTestApplication {

    @Test
    public void homeIsDisplayedAsFirstPageWhenLoggedIn() {
        driver.get(url);

        homeView.verifyAtView();
    }
}
