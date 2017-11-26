package urchin.selenium.view.groups.group;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import urchin.selenium.view.PageView;

public class EditGroupView extends PageView {

    @Override
    public EditGroupView verifyAtView() {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("edit-group")));
        return this;
    }

    public void clickDeleteGroupButton() {
        driver.findElement(By.id("edit-group__delete-btn")).click();
    }

    public EditGroupView verifyUserListed(String username) {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.linkText(username)));
        return this;
    }

    public void clickUserLink(String username) {
        driver.findElement(By.linkText(username)).click();
    }
}
