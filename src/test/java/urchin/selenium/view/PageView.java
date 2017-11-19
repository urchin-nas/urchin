package urchin.selenium.view;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class PageView<T> {

    @Autowired
    @Qualifier("seleniumWebDriver")
    WebDriver driver;

    @Autowired
    @Qualifier("seleniumUrl")
    String url;

    public abstract T verifyAtView();

    WebElement waitUntil(ExpectedCondition<WebElement> webElementExpectedCondition) {
        WebDriverWait wait = new WebDriverWait(driver, 3);
        return wait.until(webElementExpectedCondition);
    }
}
