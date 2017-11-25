package urchin.selenium.view;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import urchin.selenium.testutil.SeleniumDriver;
import urchin.selenium.testutil.SeleniumUrl;

public abstract class PageView<T> {

    private static final int TIME_OUT_IN_SECONDS = 3;

    protected final WebDriver driver = SeleniumDriver.getDriver();
    protected final String url = SeleniumUrl.getUrl();

    public abstract T verifyAtView();

    protected WebElement waitUntil(ExpectedCondition<WebElement> webElementExpectedCondition) {
        WebDriverWait wait = new WebDriverWait(driver, TIME_OUT_IN_SECONDS);
        return wait.until(webElementExpectedCondition);
    }
}
