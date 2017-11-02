package urchin.repository;

import org.junit.Before;
import org.junit.Test;
import urchin.exception.UserNotFoundException;
import urchin.model.user.User;
import urchin.model.user.UserId;
import urchin.model.user.Username;
import urchin.testutil.TestApplication;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class UserRepositoryIT extends TestApplication {

    private static final Username USERNAME = Username.of("username");

    private UserRepository userRepository;

    @Before
    public void setup() {
        userRepository = new UserRepository(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Test
    public void crd() {
        LocalDateTime now = LocalDateTime.now();

        UserId userId = userRepository.saveUser(USERNAME);
        User readUser = userRepository.getUser(userId);

        assertEquals(USERNAME, readUser.getUsername());
        assertFalse(now.isAfter(readUser.getCreated()));

        userRepository.removeUser(userId);

        try {
            userRepository.getUser(userId);
            fail("expected exception");
        } catch (UserNotFoundException ignore) {

        }
    }

    @Test
    public void getUsersByUsernameReturnsUsers() {
        Username username_1 = Username.of("username_1");
        Username username_2 = Username.of("username_2");
        Username username_3 = Username.of("username_3");
        userRepository.saveUser(username_1);
        userRepository.saveUser(username_2);
        userRepository.saveUser(username_3);

        List<User> users = userRepository.getUsersByUsername(Arrays.asList(username_1, username_3));

        List<Username> usernames = users.stream()
                .map(User::getUsername)
                .collect(Collectors.toList());
        assertTrue(usernames.contains(username_1));
        assertTrue(usernames.contains(username_3));
        assertFalse(usernames.contains(username_2));
    }
}