package urchin.selenium.view;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import urchin.selenium.testutil.SeleniumDriver;
import urchin.selenium.testutil.SeleniumUrl;

public abstract class PageView<T> {

    private static final int TIME_OUT_IN_SECONDS = 3;

    protected final WebDriver driver = SeleniumDriver.getDriver();

    private final String url = SeleniumUrl.getUrl();

    private final WebDriverWait wait = new WebDriverWait(driver, TIME_OUT_IN_SECONDS);

    protected abstract String viewUrl();

    public abstract T verifyAtView();

    public T goTo() {
        driver.get(url + viewUrl());
        return verifyAtView();
    }

    @SuppressWarnings("unchecked")
    protected void waitUntil(ExpectedCondition webElementExpectedCondition) {
        wait.until(webElementExpectedCondition);
    }

    protected By byDataView(String name) {
        return By.cssSelector("[data-view=" + name + "]");
    }


}
