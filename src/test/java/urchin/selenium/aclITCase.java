package urchin.selenium;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import urchin.selenium.testutil.SeleniumTest;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static urchin.testutil.UnixUserAndGroupCleanup.GROUP_PREFIX;
import static urchin.testutil.UnixUserAndGroupCleanup.USERNAME_PREFIX;

public class aclITCase extends SeleniumTest {

    @ClassRule
    public static TemporaryFolder temporaryFolder = new TemporaryFolder();

    private static String groupName;
    private static String username;
    private static String folderName;
    private static String folderPath;

    @BeforeClass
    public static void setUp() {
        groupName = GROUP_PREFIX + System.currentTimeMillis();
        username = USERNAME_PREFIX + System.currentTimeMillis();
        folderName = "test-" + System.currentTimeMillis();
        folderPath = temporaryFolder.getRoot().getAbsolutePath() + "/" + folderName;

        NEW_GROUP.goTo()
                .fillGroupName(groupName)
                .clickOnCreateGroup();

        GROUPS.verifyGroupNameListed(groupName);

        NEW_USER.goTo()
                .fillUsername(username)
                .fillPassword(randomAlphanumeric(10))
                .clickOnCreateUser();

        USERS.verifyUsernameListed(username);

        NEW_FOLDER.goTo()
                .fillFolderPath(folderPath)
                .clickOnCreateFolder();

        FOLDERS.verifyFolderListed(folderName);
    }

    @Test
    public void creatingAndDeletingFolder() {

        FOLDERS.goTo()
                .clickOnFolder(folderName);

    }
}
