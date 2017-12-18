package urchin.selenium.view.users;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import urchin.selenium.view.PageView;

import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated;

public class UsersView extends PageView<UsersView> {

    @Override
    protected String viewUrl() {
        return "/users";
    }

    @Override
    public UsersView verifyAtView() {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(byDataView("users")));
        return this;
    }

    public UsersView verifyUsernameListed(String username) {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.linkText(username)));
        return this;
    }

    public UsersView verifyUsernameNotListed(String username) {
        waitUntil(invisibilityOfElementLocated(By.linkText(username)));
        return this;
    }

    public void clickOnCreateNewUser() {
        driver.findElement(byDataView("newUser")).click();
    }

    public void clickOnUsername(String username) {
        driver.findElement(By.linkText(username)).click();
    }

}
