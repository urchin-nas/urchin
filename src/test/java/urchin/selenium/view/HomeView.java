package urchin.selenium.view;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomeView extends PageView {

    @Override
    public HomeView verifyAtView() {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("home")));
        return this;
    }

    public HomeView goTo() {
        driver.get(url);
        return verifyAtView();
    }

}
