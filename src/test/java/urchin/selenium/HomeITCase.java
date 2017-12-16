package urchin.selenium;

import org.junit.Test;
import urchin.selenium.testutil.SeleniumTestApplication;

public class HomeITCase extends SeleniumTestApplication {

    @Test
    public void homeIsDisplayedAsFirstPageWhenLoggedIn() {
        DRIVER.get(URL);

        HOME.verifyAtView();
    }
}
