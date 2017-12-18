package urchin.selenium.view;

import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomeView extends PageView<HomeView> {

    @Override
    protected String viewUrl() {
        return "/";
    }

    @Override
    public HomeView verifyAtView() {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(byDataView("home")));
        return this;
    }
}
