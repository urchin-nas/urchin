package urchin.selenium.view.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import urchin.selenium.view.PageView;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class LoginView extends PageView<LoginView> {

    @Override
    protected String viewUrl() {
        return "/login";
    }

    @Override
    public LoginView verifyAtView() {
        waitUntil(visibilityOfElementLocated(byDataView("login")));
        return this;
    }

    public String goToHome() {
        driver.get(url);

        ExpectedCondition<WebElement> login = visibilityOfElementLocated(byDataView("login"));
        ExpectedCondition<WebElement> setupAdmin = visibilityOfElementLocated(byDataView("setupAdmin"));
        ExpectedCondition<WebElement> home = visibilityOfElementLocated(byDataView("home"));

        waitUntilEither(login, setupAdmin, home);

        return driver.getCurrentUrl();
    }

    public LoginView fillUsername(String username) {
        driver.findElement(By.name("username")).sendKeys(username);
        return this;
    }

    public LoginView fillpassword(String password) {
        driver.findElement(By.name("password")).sendKeys(password);
        return this;
    }

    public void clickOnLogin() {
        driver.findElement(By.name("login")).click();
    }

}
