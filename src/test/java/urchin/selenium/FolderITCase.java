package urchin.selenium;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import urchin.selenium.testutil.SeleniumTest;

public class FolderITCase extends SeleniumTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void creatingAndDeletingFolder() {
        String folderName = "test-" + System.currentTimeMillis();
        String folderPath = temporaryFolder.getRoot().getAbsolutePath() + "/" + folderName;

        HOME.goTo();

        NAVIGATION.verifyAtView()
                .clickFoldersLink();

        FOLDERS.verifyAtView()
                .clickCreateNewFolderLink();

        NEW_FOLDER.verifyAtView()
                .clickCancelButton();

        FOLDERS.verifyAtView()
                .clickCreateNewFolderLink();

        NEW_FOLDER.verifyAtView()
                .fillFolderPath(folderPath)
                .clickCreateFolderButton();

        FOLDERS.verifyAtView()
                .verifyFolderListed(folderName)
                .clickFolderLink(folderName);

        EDIT_FOLDER.verifyAtView()
                .clickBackButton();

        FOLDERS.verifyAtView()
                .verifyFolderListed(folderName)
                .clickFolderLink(folderName);

        EDIT_FOLDER.verifyAtView()
                .clickDeleteFólderButton();

        FOLDERS.verifyAtView()
                .verifyFolderNotListed(folderName);
    }
}
