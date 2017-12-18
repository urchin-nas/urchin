package urchin.selenium.view;

import org.openqa.selenium.support.ui.ExpectedConditions;

public class NavigationView extends PageView<NavigationView> {

    @Override
    protected String viewUrl() {
        return "/";
    }

    @Override
    public NavigationView verifyAtView() {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(byDataView("navigation")));
        return this;
    }


    public void clickUsersLink() {
        driver.findElement(byDataView("users")).click();
    }

    public void clickGroupsLink() {
        driver.findElement(byDataView("groups")).click();
    }

    public void clickFoldersLink() {
        driver.findElement(byDataView("folders")).click();
    }
}
