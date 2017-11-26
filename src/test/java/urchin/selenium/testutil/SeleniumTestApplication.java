package urchin.selenium.testutil;

import org.junit.Rule;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import urchin.selenium.view.HomeView;
import urchin.selenium.view.MenuView;
import urchin.selenium.view.folders.FoldersView;
import urchin.selenium.view.folders.folder.EditFolderView;
import urchin.selenium.view.folders.folder.NewFolderView;
import urchin.selenium.view.groups.GroupsView;
import urchin.selenium.view.groups.group.EditGroupView;
import urchin.selenium.view.groups.group.NewGroupView;
import urchin.selenium.view.users.UsersView;
import urchin.selenium.view.users.user.EditUserView;
import urchin.selenium.view.users.user.NewUserView;
import urchin.testutil.CliTestConfiguration;
import urchin.testutil.UnixUserAndGroupCleanup;

@RunWith(SeleniumRunner.class)
@Import(CliTestConfiguration.class)
public abstract class SeleniumTestApplication {

    private static Logger log = LoggerFactory.getLogger(SeleniumTestApplication.class);

    protected static final WebDriver driver = SeleniumDriver.getDriver();
    protected static final String url = SeleniumUrl.getUrl();

    @Rule
    @Autowired
    public UnixUserAndGroupCleanup unixUserAndGroupCleanup;

    private static boolean initialized;

    protected static HomeView homeView;
    protected static MenuView menuView;
    protected static GroupsView groupsView;
    protected static NewGroupView newGroupView;
    protected static EditGroupView editGroupView;
    protected static UsersView usersView;
    protected static NewUserView newUserView;
    protected static EditUserView editUserView;
    protected static FoldersView foldersView;
    protected static NewFolderView newFolderView;
    protected static EditFolderView editFolderView;

    public SeleniumTestApplication() {
        if (!initialized) {
            log.info("Setting up PageViews");
            homeView = new HomeView();
            menuView = new MenuView();
            groupsView = new GroupsView();
            newGroupView = new NewGroupView();
            editGroupView = new EditGroupView();
            usersView = new UsersView();
            newUserView = new NewUserView();
            editUserView = new EditUserView();
            foldersView = new FoldersView();
            newFolderView = new NewFolderView();
            editFolderView = new EditFolderView();

            initialized = true;
        }
    }
}
