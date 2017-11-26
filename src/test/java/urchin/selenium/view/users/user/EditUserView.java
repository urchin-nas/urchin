package urchin.selenium.view.users.user;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import urchin.selenium.view.PageView;

import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class EditUserView extends PageView {

    @Override
    public EditUserView verifyAtView() {
        waitUntil(visibilityOfElementLocated(By.id("edit-user")));
        return this;
    }

    public void clickDeleteUserButton() {
        driver.findElement(By.id("edit-user__delete-btn")).click();
    }

    public EditUserView selectGroup(String groupName) {
        Select groupSelect = new Select(driver.findElement(By.id("edit-user__available-groups")));
        groupSelect.selectByVisibleText(groupName);
        return this;
    }

    public EditUserView clickAddGroupButton() {
        driver.findElement(By.id("edit-user__add-group-btn")).click();
        return this;
    }

    public EditUserView verifyGroupListed(String groupName) {
        waitUntil(visibilityOfElementLocated(By.linkText(groupName)));
        return this;
    }

    public EditUserView verifyGroupNotListed(String groupName) {
        waitUntil(invisibilityOfElementLocated(By.linkText(groupName)));
        return this;
    }

    public void clickGroupLink(String groupName) {
        driver.findElement(By.linkText(groupName)).click();
    }

    public EditUserView clickRemoveGroupButton(String groupName) {
        driver.findElements(By.className("edit-user__groups__item")).stream()
                .filter(webElement -> webElement.findElements(By.linkText(groupName)).size() > 0)
                .findFirst()
                .get()
                .findElement(By.className("edit-user__groups__item__remove-group-btn"))
                .click();
        return this;
    }

    public void clickBackButton() {
        driver.findElement(By.id("edit-user__back-btn")).click();
    }
}
