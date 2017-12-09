package urchin.selenium.view;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class NavigationView extends PageView {

    @Override
    public NavigationView verifyAtView() {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("navigation")));
        return this;
    }

    public void clickUsersLink() {
        driver.findElement(By.id("navigation-item__users")).click();
    }

    public void clickGroupsLink() {
        driver.findElement(By.id("navigation-item__groups")).click();
    }

    public void clickFoldersLink() {
        driver.findElement(By.id("navigation-item__folders")).click();
    }
}
