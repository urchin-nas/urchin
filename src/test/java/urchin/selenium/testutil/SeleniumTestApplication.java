package urchin.selenium.testutil;

import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
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

@RunWith(SeleniumRunner.class)
public abstract class SeleniumTestApplication {

    protected static final WebDriver driver = SeleniumDriver.getDriver();
    protected static final String url = SeleniumUrl.getUrl();

    protected static HomeView homeView = new HomeView();
    protected static MenuView menuView = new MenuView();
    protected static GroupsView groupsView = new GroupsView();
    protected static NewGroupView newGroupView = new NewGroupView();
    protected static EditGroupView editGroupView = new EditGroupView();
    protected static UsersView usersView = new UsersView();
    protected static NewUserView newUserView = new NewUserView();
    protected static EditUserView editUserView = new EditUserView();
    protected static FoldersView foldersView = new FoldersView();
    protected static NewFolderView newFolderView = new NewFolderView();
    protected static EditFolderView editFolderView = new EditFolderView();

}
