package urchin.cli;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import urchin.model.group.GroupName;
import urchin.model.user.*;
import urchin.testutil.CliTestConfiguration;
import urchin.testutil.UnixUserAndGroupCleanup;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static urchin.testutil.UnixUserAndGroupCleanup.USERNAME_PREFIX;

@RunWith(SpringRunner.class)
@Import(CliTestConfiguration.class)
public class UserCliIT {

    private static final Password PASSWORD = Password.of("superSecret");
    private static final Shadow SHADOW = ImmutableShadow.builder()
            .id("6")
            .salt("iDvr6NoE")
            .encryptedPassword("L18aEvySgnBP3lCW0wgDzsLzx6DDeoLYNctxkJB4i7mbkaqEtmuxZwIPn8NWJ6wjlGtzYmfPMUX05VWtqlFru.")
            .build();

    @Rule
    @Autowired
    public UnixUserAndGroupCleanup unixUserAndGroupCleanup;

    @Autowired
    private UserCli userCli;

    private User user;
    private Username username;


    @Before
    public void setUp() {
        username = Username.of(USERNAME_PREFIX + System.currentTimeMillis());
        user = ImmutableUser.builder()
                .userId(UserId.of(1))
                .username(username)
                .created(LocalDateTime.now())
                .build();
    }

    @Test
    public void addUserAndSetUserPasswordAndCheckIfUsernameExistAndRemoveUserAreExecutedSuccessfully() {
        userCli.addUser(username);
        userCli.setUserPassword(username, PASSWORD);

        assertThat(userCli.checkIfUsernameExist(username)).isTrue();

        userCli.removeUser(username);

        assertThat(userCli.checkIfUsernameExist(username)).isFalse();
    }

    @Test
    public void listUsersReturnsListOfUsers() {
        List<String> strings = userCli.listUsers();

        assertThat(strings.isEmpty()).isFalse();
    }

    @Test
    public void listGroupsForUserReturnsGroups() {
        userCli.addUser(user.getUsername());

        List<GroupName> groups = userCli.listGroupsForUser(user);

        assertThat(groups).hasSize(1);
    }

    @Test
    public void whoAmIReturnsMe() {
        LinuxUser linuxUser = userCli.whoAmI();

        assertThat(linuxUser).isNotNull();
    }

    @Test
    public void getShadowReturnsShadow() {
        LinuxUser linuxUser = userCli.whoAmI();

        Shadow shadow = userCli.getShadow(linuxUser);

        assertThat(shadow).isNotNull();
    }

    @Test
    public void verifyShadowPasswordCommandReturnsTrueWhenEncryptedPasswordsMatch() {
        assertThat(userCli.verifyShadowPassword(PASSWORD, SHADOW)).isTrue();
    }

    @Test
    public void verifyShadowPasswordCommandReturnsFalseWhenEncryptedPasswordsDoesNotMatch() {
        assertThat(userCli.verifyShadowPassword(Password.of("invalidPassword"), SHADOW)).isFalse();
    }

}