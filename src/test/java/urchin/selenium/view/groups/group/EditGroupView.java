package urchin.selenium.view.groups.group;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import urchin.selenium.view.PageView;

import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated;

public class EditGroupView extends PageView<EditGroupView> {

    @Override
    protected String viewUrl() {
        return "/groups/edit/0";
    }

    @Override
    public EditGroupView verifyAtView() {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(byDataView("editGroup")));
        return this;
    }

    public void clickOnDeleteGroup() {
        driver.findElement(byDataView("delete")).click();
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
        Select groupSelect = new Select(driver.findElement(byDataView("availableUsers")));
        groupSelect.selectByVisibleText(username);
        return this;
    }

    public EditGroupView clickAddUserButton() {
        driver.findElement(byDataView("addUser")).click();
        return this;
    }

    public EditGroupView clickRemoveUserButton(String username) {
        driver.findElements(byDataView("users")).stream()
                .filter(webElement -> webElement.findElements(By.linkText(username)).size() > 0)
                .findFirst()
                .get()
                .findElement(byDataView("removeUser"))
                .click();
        return this;
    }

    public void clickOnBack() {
        driver.findElement(byDataView("back")).click();
    }
}
