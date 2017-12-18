package urchin.selenium.view.users.user;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import urchin.selenium.view.PageView;

import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class EditUserView extends PageView<EditUserView> {

    @Override
    protected String viewUrl() {
        return "/users/edit/0";
    }

    @Override
    public EditUserView verifyAtView() {
        waitUntil(visibilityOfElementLocated(byDataView("editUser")));
        return this;
    }

    public void clickOnDeleteUser() {
        driver.findElement(byDataView("delete")).click();
    }

    public EditUserView selectGroup(String groupName) {
        Select groupSelect = new Select(driver.findElement(byDataView("availableGroups")));
        groupSelect.selectByVisibleText(groupName);
        return this;
    }

    public EditUserView clickAddGroupButton() {
        driver.findElement(byDataView("addGroup")).click();
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
        driver.findElements(byDataView("groups")).stream()
                .filter(webElement -> webElement.findElements(By.linkText(groupName)).size() > 0)
                .findFirst()
                .get()
                .findElement(byDataView("removeGroup"))
                .click();
        return this;
    }

    public void clickOnBack() {
        driver.findElement(byDataView("back")).click();
    }
}
