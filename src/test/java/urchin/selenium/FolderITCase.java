package urchin.selenium;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import urchin.selenium.testutil.SeleniumTestApplication;

public class FolderITCase extends SeleniumTestApplication {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void createFolder() {
        String folderName = "test-" + System.currentTimeMillis();
        String folderPath = temporaryFolder.getRoot().getAbsolutePath() + "/" + folderName;

        homeView.goTo();

        menuView.verifyAtView()
                .clickFoldersLink();

        foldersView.verifyAtView()
                .clickCreateNewFolderLink();

        newFolderView.verifyAtView()
                .fillFolderPath(folderPath)
                .clickCreateFolderButton();

        foldersView.verifyAtView()
                .verifyFolderListed(folderName)
                .clickFolderLink(folderName);

        editFolderView.verifyAtView();
    }
}
