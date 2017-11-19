package urchin.selenium.view;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class HomeView extends PageView {

    public HomeView goToView() {
        driver.get(url);
        return verifyAtView();
    }

    @Override
    public HomeView verifyAtView() {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("home")));
        return this;
    }

}
