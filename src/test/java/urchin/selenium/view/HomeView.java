package urchin.selenium.view;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomeView extends PageView<HomeView> {

    @Override
    protected String viewUrl() {
        return "/";
    }

    @Override
    public HomeView verifyAtView() {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("home")));
        return this;
    }
}
