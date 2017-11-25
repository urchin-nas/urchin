package urchin.selenium.testutil;

import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import urchin.selenium.view.HomeView;
import urchin.selenium.view.MenuView;
import urchin.selenium.view.PageView;
import urchin.selenium.view.groups.GroupsView;
import urchin.selenium.view.groups.group.EditGroupView;
import urchin.selenium.view.groups.group.NewGroupView;
import urchin.selenium.view.users.UsersView;
import urchin.selenium.view.users.user.EditUserView;
import urchin.selenium.view.users.user.NewUserView;

@RunWith(SeleniumRunner.class)
@ComponentScan(basePackageClasses = PageView.class)
@Import(HomeView.class)
public abstract class SeleniumTestApplication {

    protected final WebDriver driver = SeleniumDriver.getDriver();
    protected final String url = SeleniumUrl.getUrl();

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

}
