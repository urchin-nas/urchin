package urchin.selenium.view.groups.group;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import urchin.selenium.view.PageView;

import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated;

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

    public EditGroupView verifyUserNotListed(String username) {
        waitUntil(invisibilityOfElementLocated(By.linkText(username)));
        return this;
    }

    public void clickUserLink(String username) {
        driver.findElement(By.linkText(username)).click();
    }

    public EditGroupView selectUser(String username) {
        Select groupSelect = new Select(driver.findElement(By.id("edit-group__available-users")));
        groupSelect.selectByVisibleText(username);
        return this;
    }

    public EditGroupView clickAddUserButton() {
        driver.findElement(By.id("edit-group__add-user-btn")).click();
        return this;
    }

    public EditGroupView clickRemoveUserButton(String username) {
        driver.findElements(By.className("edit-group__users__item")).stream()
                .filter(webElement -> webElement.findElements(By.linkText(username)).size() > 0)
                .findFirst()
                .get()
                .findElement(By.className("edit-group__users__item__remove-user-btn"))
                .click();
        return this;
    }
}
