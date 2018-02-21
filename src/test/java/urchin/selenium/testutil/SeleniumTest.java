package urchin.selenium.testutil;

import org.junit.runner.RunWith;
import urchin.selenium.view.HomeView;
import urchin.selenium.view.NavigationView;
import urchin.selenium.view.folders.FoldersView;
import urchin.selenium.view.folders.folder.ConfirmNewFolderView;
import urchin.selenium.view.folders.folder.EditFolderView;
import urchin.selenium.view.folders.folder.NewFolderView;
import urchin.selenium.view.groups.GroupsView;
import urchin.selenium.view.groups.group.EditGroupView;
import urchin.selenium.view.groups.group.NewGroupView;
import urchin.selenium.view.users.UsersView;
import urchin.selenium.view.users.user.EditUserView;
import urchin.selenium.view.users.user.NewUserView;

@RunWith(SeleniumRunner.class)
public abstract class SeleniumTest {

    protected static final HomeView HOME = new HomeView();
    protected static final NavigationView NAVIGATION = new NavigationView();
    protected static final GroupsView GROUPS = new GroupsView();
    protected static final NewGroupView NEW_GROUP = new NewGroupView();
    protected static final EditGroupView EDIT_GROUP = new EditGroupView();
    protected static final UsersView USERS = new UsersView();
    protected static final NewUserView NEW_USER = new NewUserView();
    protected static final EditUserView EDIT_USER = new EditUserView();
    protected static final FoldersView FOLDERS = new FoldersView();
    protected static final NewFolderView NEW_FOLDER = new NewFolderView();
    protected static final EditFolderView EDIT_FOLDER = new EditFolderView();
    protected static final ConfirmNewFolderView CONFIRM_NEW_FOLDER = new ConfirmNewFolderView();

}
