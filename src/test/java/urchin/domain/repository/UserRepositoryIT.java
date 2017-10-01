package urchin.domain.repository;

import org.junit.Before;
import org.junit.Test;
import urchin.domain.model.User;
import urchin.domain.model.UserId;
import urchin.exception.UserNotFoundException;
import urchin.testutil.TestApplication;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class UserRepositoryIT extends TestApplication {

    private static final String USERNAME = "username";

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