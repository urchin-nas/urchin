package urchin.repository;

import org.junit.Before;
import org.junit.Test;
import urchin.exception.UserNotFoundException;
import urchin.model.user.User;
import urchin.model.user.UserId;
import urchin.model.user.Username;
import urchin.testutil.TestApplication;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class UserRepositoryIT extends TestApplication {

    private static final Username USERNAME = Username.of("username");

    private UserRepository userRepository;

    @Before
    public void setup() {
        userRepository = new UserRepository(jdbcTemplate);
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
}