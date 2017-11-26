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

    protected static final HomeView homeView = new HomeView();
    protected static final MenuView menuView = new MenuView();
    protected static final GroupsView groupsView = new GroupsView();
    protected static final NewGroupView newGroupView = new NewGroupView();
    protected static final EditGroupView editGroupView = new EditGroupView();
    protected static final UsersView usersView = new UsersView();
    protected static final NewUserView newUserView = new NewUserView();
    protected static final EditUserView editUserView = new EditUserView();
    protected static final FoldersView foldersView = new FoldersView();
    protected static final NewFolderView newFolderView = new NewFolderView();
    protected static final EditFolderView editFolderView = new EditFolderView();

}
