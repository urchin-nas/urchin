package urchin.selenium.view.folders.folder;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import urchin.selenium.view.PageView;

public class ConfirmNewFolderView extends PageView<ConfirmNewFolderView> {

    @Override
    protected String viewUrl() {
        return "/folders/new/confirm";
    }

    @Override
    public ConfirmNewFolderView verifyAtView() {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(byDataView("confirmNewFolder")));
        return this;
    }

    public ConfirmNewFolderView fillPassphrase() {
        String passphraseToMatch = driver.findElement(byDataView("passphraseToMatch")).getText();
        driver.findElement(By.name("passphrase")).sendKeys(passphraseToMatch);
        return this;
    }

    public void clickOnConfirm() {
        driver.findElement(byDataView("confirm")).click();
    }
}
