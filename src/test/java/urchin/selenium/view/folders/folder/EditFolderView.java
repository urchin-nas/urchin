package urchin.selenium.view.folders.folder;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.boot.test.context.TestComponent;
import urchin.selenium.view.PageView;

@TestComponent
public class EditFolderView extends PageView {

    @Override
    public EditFolderView verifyAtView() {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("edit-folder")));
        return this;
    }
}
