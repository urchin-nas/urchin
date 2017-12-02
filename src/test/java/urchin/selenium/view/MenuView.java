package urchin.selenium.view;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MenuView extends PageView {

    @Override
    public MenuView verifyAtView() {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("main-menu")));
        return this;
    }

    public void clickUsersLink() {
        driver.findElement(By.id("main-menu__links__users")).click();
    }

    public void clickGroupsLink() {
        driver.findElement(By.id("main-menu__links__groups")).click();
    }

    public void clickFoldersLink() {
        driver.findElement(By.id("main-menu__links__folders")).click();
    }
}
