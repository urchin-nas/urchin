package urchin.selenium.view.folders.folder;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import urchin.selenium.view.PageView;

public class EditFolderView extends PageView {

    @Override
    public EditFolderView verifyAtView() {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("edit-folder")));
        return this;
    }

    public void clickBackButton() {
        driver.findElement(By.id("edit-folder__back-btn")).click();
    }

    public void clickDeleteFólderButton() {
        driver.findElement(By.id("edit-folder__delete-btn")).click();
    }
}
