package urchin.selenium.view.users.user;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import urchin.selenium.view.PageView;

public class NewUserView extends PageView<NewUserView> {

    @Override
    protected String viewUrl() {
        return "/users/new";
    }

    @Override
    public NewUserView verifyAtView() {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(byDataView("newUser")));
        return this;
    }

    public NewUserView fillUsername(String username) {
        driver.findElement(By.name("username")).sendKeys(username);
        return this;
    }

    public NewUserView fillPassword(String password) {
        driver.findElement(By.name("password")).sendKeys(password);
        return this;
    }

    public void clickOnCreateUser() {
        driver.findElement(byDataView("create")).click();
    }

    public void clickOnCancel() {
        driver.findElement(byDataView("cancel")).click();
    }
}
