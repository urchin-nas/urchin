package urchin.selenium.testutil;

import org.junit.Rule;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import urchin.selenium.view.HomeView;
import urchin.selenium.view.MenuView;
import urchin.selenium.view.PageView;
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
@ComponentScan(basePackageClasses = PageView.class)
@Import(CliTestConfiguration.class)
public abstract class SeleniumTestApplication {

    protected final WebDriver driver = SeleniumDriver.getDriver();
    protected final String url = SeleniumUrl.getUrl();

    @Rule
    @Autowired
    public UnixUserAndGroupCleanup unixUserAndGroupCleanup;
    @Autowired
    protected HomeView homeView;
    @Autowired
    protected MenuView menuView;
    @Autowired
    protected GroupsView groupsView;
    @Autowired
    protected NewGroupView newGroupView;
    @Autowired
    protected EditGroupView editGroupView;
    @Autowired
    protected UsersView usersView;
    @Autowired
    protected NewUserView newUserView;
    @Autowired
    protected EditUserView editUserView;
    @Autowired
    protected FoldersView foldersView;
    @Autowired
    protected NewFolderView newFolderView;
    @Autowired
    protected EditFolderView editFolderView;

}
