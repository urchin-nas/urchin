package urchin.domain.repository;

import org.junit.Before;
import org.junit.Test;
import urchin.domain.exception.UserNotFoundException;
import urchin.domain.model.User;
import urchin.domain.model.UserId;
import urchin.testutil.TestApplication;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class UserRepositoryIT extends TestApplication {

    private UserRepository userRepository;

    @Before
    public void setup() {
        userRepository = new UserRepository(jdbcTemplate);
    }

    @Test
    public void crd() {
        LocalDateTime now = LocalDateTime.now();
        User user = new User("username");

        UserId userId = userRepository.saveUser(user);
        User readUser = userRepository.getUser(userId);

        assertEquals(user.getUsername(), readUser.getUsername());
        assertFalse(now.isAfter(readUser.getCreated()));

        userRepository.removeUser(userId);

        try {
            userRepository.getUser(userId);
            fail("expected exception");
        } catch (UserNotFoundException ignore) {

        }
    }
}