package urchin.selenium.testutil;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.core.env.PropertySource;
import urchin.cli.Command;
import urchin.cli.user.AddUserCommand;
import urchin.cli.user.SetUserPasswordCommand;
import urchin.configuration.CommandConfiguration;
import urchin.model.user.Password;
import urchin.model.user.Username;
import urchin.selenium.view.HomeView;
import urchin.selenium.view.NavigationView;
import urchin.selenium.view.folders.FoldersView;
import urchin.selenium.view.folders.folder.ConfirmNewFolderView;
import urchin.selenium.view.folders.folder.EditFolderView;
import urchin.selenium.view.folders.folder.NewFolderView;
import urchin.selenium.view.groups.GroupsView;
import urchin.selenium.view.groups.group.EditGroupView;
import urchin.selenium.view.groups.group.NewGroupView;
import urchin.selenium.view.login.LoginView;
import urchin.selenium.view.setup.SetupAdminView;
import urchin.selenium.view.users.UsersView;
import urchin.selenium.view.users.user.EditUserView;
import urchin.selenium.view.users.user.NewUserView;

import java.io.IOException;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static urchin.testutil.UnixUserAndGroupCleanup.USERNAME_PREFIX;

@RunWith(SeleniumRunner.class)
public abstract class SeleniumTest {

    private static String username;
    private static String password;

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
    private static final SetupAdminView SETUP_ADMIN_VIEW = new SetupAdminView();
    private static final LoginView LOGIN = new LoginView();

    @BeforeClass
    public static void setUpAndLogin() throws IOException {
        String redirectUrl = LOGIN.goToHome();

        if (redirectUrl.endsWith("/login")) {
            login();
        } else if (redirectUrl.endsWith("/setup-admin")) {
            setupAdmin();
            setUpAndLogin();
        }
    }

    private static void setupAdmin() throws IOException {
        CommandConfiguration commandConfiguration = new CommandConfiguration();
        PropertySource propertySource = commandConfiguration.yamlPropertySourceLoader();
        Command command = new Command(propertySource);
        AddUserCommand addUserCommand = new AddUserCommand(Runtime.getRuntime(), command);
        SetUserPasswordCommand setUserPasswordCommand = new SetUserPasswordCommand(Runtime.getRuntime(), command);

        Username user = Username.of(USERNAME_PREFIX + System.currentTimeMillis());
        Password pwd = Password.of(randomAlphanumeric(10));
        addUserCommand.execute(user);
        setUserPasswordCommand.execute(user, pwd);

        SETUP_ADMIN_VIEW.verifyAtView()
                .fillUsername(user.getValue())
                .clickOnAddAdmin();

        username = user.getValue();
        password = pwd.getValue();
    }

    private static void login() {
        LOGIN.fillUsername(username)
                .fillpassword(password)
                .clickOnLogin();
        HOME.verifyAtView();
    }

}
