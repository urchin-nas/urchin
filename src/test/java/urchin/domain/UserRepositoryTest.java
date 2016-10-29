package urchin.domain;

import org.junit.Before;
import org.junit.Test;
import urchin.domain.model.User;
import urchin.domain.model.UserId;
import urchin.testutil.H2Application;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.*;

public class UserRepositoryTest extends H2Application {

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
        Optional<User> userOptional = userRepository.getUser(userId);

        assertTrue(userOptional.isPresent());
        User readUser = userOptional.get();
        assertEquals(user.getUsername(), readUser.getUsername());
        assertTrue(now.isBefore(readUser.getCreated()));

        userRepository.removeUser(userId);

        assertFalse(userRepository.getUser(userId).isPresent());
    }
}