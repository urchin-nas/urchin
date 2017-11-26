package urchin.selenium.view.folders.folder;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import urchin.selenium.view.PageView;

public class NewFolderView extends PageView {

    @Override
    public NewFolderView verifyAtView() {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("new-folder")));
        return this;
    }

    public NewFolderView fillFolderPath(String folderPath) {
        driver.findElement(By.name("folder")).sendKeys(folderPath);
        return this;
    }

    public void clickCreateFolderButton() {
        driver.findElement(By.id("new-folder__create-btn")).click();
    }

    public void clickCancelButton() {
        driver.findElement(By.id("new-folder__cancel-btn")).click();
    }
}
