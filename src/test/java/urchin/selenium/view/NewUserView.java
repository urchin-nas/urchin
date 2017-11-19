package urchin.selenium.view;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class NewUserView extends PageView {

    @Override
    public NewUserView verifyAtView() {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("new-user")));
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

    public void clickCreateUserButton() {
        driver.findElement(By.id("new-user__create-btn")).click();
    }
}
